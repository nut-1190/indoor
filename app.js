// Global variables
let locations = [];
let currentLocation = null;
let destinationLocation = null;
let html5QrCode = null;

// DOM Elements
const floorPlanImg = document.getElementById('floor-plan-img');
const routeOverlay = document.getElementById('route-overlay');
const currentMarker = document.getElementById('current-marker');
const destinationMarker = document.getElementById('destination-marker');
const sourceDropdown = document.getElementById('source-dropdown');
const destinationDropdown = document.getElementById('destination-dropdown');
const navigateBtn = document.getElementById('navigate-btn');
const scanQrBtn = document.getElementById('scan-qr');
const qrScannerModal = document.getElementById('qr-scanner-modal');
const closeBtn = document.querySelector('.close-btn');
const directionsList = document.getElementById('directions-list');

function adjustCanvasSize() {
    const floorPlanImg = document.getElementById("floor-plan-img");
    const canvas = document.getElementById("route-overlay");

    if (!floorPlanImg.complete) {
        floorPlanImg.onload = adjustCanvasSize; // Re-run once image loads
        return;
    }

    canvas.width = floorPlanImg.clientWidth;
    canvas.height = floorPlanImg.clientHeight;

    console.log(`Canvas size set: ${canvas.width}x${canvas.height}`);
}

// Ensure the canvas is properly sized when the page loads or resizes
window.onload = adjustCanvasSize;
window.onresize = adjustCanvasSize;

// Set up the canvas for route drawing
function setupCanvas() {
    const canvas = routeOverlay;
    canvas.width = floorPlanImg.width;
    canvas.height = floorPlanImg.height;

    floorPlanImg.onload = () => {
        canvas.width = floorPlanImg.clientWidth;
        canvas.height = floorPlanImg.clientHeight;
    };

    window.addEventListener('resize', () => {
        canvas.width = floorPlanImg.clientWidth;
        canvas.height = floorPlanImg.clientHeight;
        if (currentLocation && destinationLocation) {
            drawRoute(currentLocation, destinationLocation);
        }
    });
}

// Initialize application
async function initialize() {
    try {
        // Load location data
        const response = await fetch('data/locations.json');
        const data = await response.json();
        locations = data.locations;
        
        populateSourceDropdown();
        populateDestinationDropdown();
        setupEventListeners();
        setupCanvas();

        // Create the arrow marker dynamically
        const arrowMarker = document.createElement("div");
        arrowMarker.classList.add("arrow-marker");
        document.body.appendChild(arrowMarker);

        console.log('Application initialized successfully');
    } catch (error) {
        console.error('Failed to initialize application:', error);
    }
}

// Populate dropdowns
function populateSourceDropdown() {
    while (sourceDropdown.options.length > 1) sourceDropdown.remove(1);
    locations.forEach(location => {
        const option = document.createElement('option');
        option.value = location.id;
        option.textContent = location.name;
        sourceDropdown.appendChild(option);
    });
}

function populateDestinationDropdown() {
    while (destinationDropdown.options.length > 1) destinationDropdown.remove(1);
    locations.forEach(location => {
        const option = document.createElement('option');
        option.value = location.id;
        option.textContent = location.name;
        destinationDropdown.appendChild(option);
    });
}

// Generate step-by-step directions
function generateDirections(path) {
    directionsList.innerHTML = '';

    if (!Array.isArray(path) || path.length === 0) {
        directionsList.innerHTML = '<li>No route found.</li>';
        return;
    }

    path.forEach((point, index) => {
        const step = document.createElement('li');
        step.textContent = `Step ${index + 1}: Move to ${point.name}`;
        directionsList.appendChild(step);
    });
}

// Set up event listeners
function setupEventListeners() {
    sourceDropdown.addEventListener('change', function () {
        const selectedId = this.value;
        if (selectedId) {
            currentLocation = locations.find(loc => loc.id === selectedId);
            updateMarker(currentMarker, currentLocation.x, currentLocation.y);
            navigateBtn.disabled = !destinationDropdown.value;
        }
    });

    destinationDropdown.addEventListener('change', function () {
        navigateBtn.disabled = !destinationDropdown.value || !currentLocation;
    });

    // Handle navigation button click
navigateBtn.addEventListener('click', () => {
    const selectedId = destinationDropdown.value;
    if (selectedId && currentLocation) {
        destinationLocation = locations.find(loc => loc.id === selectedId);
        if (destinationLocation) {
            console.log("Navigating from", currentLocation.name, "to", destinationLocation.name);
            drawRoute(currentLocation, destinationLocation);  // Ensure this is being called
            generateDirections(findPath(currentLocation, destinationLocation)); // Pass correct path
        }
    }
});


    scanQrBtn.addEventListener('click', openQrScanner);
    closeBtn.addEventListener('click', closeQrScanner);
}

// QR Scanner functions
function openQrScanner() {
    qrScannerModal.classList.remove('hidden');
    const scannerArea = document.getElementById('scanner-area');
    html5QrCode = new Html5Qrcode('scanner-area');

    html5QrCode.start(
        { facingMode: "environment" },
        { fps: 10, qrbox: { width: 250, height: 250 } },
        onQrCodeSuccess,
        onQrCodeError
    ).catch(err => console.error('QR Scanner failed:', err));
}

function closeQrScanner() {
    qrScannerModal.classList.add('hidden');
    if (html5QrCode && html5QrCode.isScanning) {
        html5QrCode.stop().catch(err => console.error('Error stopping QR scanner:', err));
    }
}

function onQrCodeSuccess(qrCodeMessage) {
    const scannedLocation = locations.find(loc => loc.id === qrCodeMessage);

    if (scannedLocation) {
        currentLocation = scannedLocation;
        updateMarker(currentMarker, scannedLocation.x, scannedLocation.y);
        sourceDropdown.value = scannedLocation.id;
        navigateBtn.disabled = !destinationDropdown.value;
        closeQrScanner();
    } else {
        alert('Unknown location: ' + qrCodeMessage);
    }
}

function onQrCodeError(err) {
    console.error('QR Scanner error:', err);
}

// Update marker position
function updateMarker(marker, x, y, prevX, prevY) {
    const scaleX = floorPlanImg.clientWidth / floorPlanImg.naturalWidth;
    const scaleY = floorPlanImg.clientHeight / floorPlanImg.naturalHeight;

    marker.style.transition = "left 0.5s linear, top 0.5s linear";
    marker.style.left = `${x * scaleX}px`;
    marker.style.top = `${y * scaleY}px`;
    marker.style.display = 'block';

    // Rotate the arrow based on movement direction
    if (prevX !== undefined && prevY !== undefined) {
        const angle = Math.atan2(y - prevY, x - prevX) * (180 / Math.PI);
        arrowMarker.style.transform = `rotate(${angle}deg)`;
        arrowMarker.style.left = `${x * scaleX}px`;
        arrowMarker.style.top = `${y * scaleY}px`;
    }
}



// Draw route between locations
// Draw route between locations with animated arrow
// Draw route with animated arrow
function drawRoute(start, end) {
    console.log("Drawing route...");
    const path = findPath(start, end);

    if (!path || path.length < 2) {
        console.error("No path found");
        return;
    }

    console.log("Path found:", path);

    // Clear previous route
    const ctx = routeOverlay.getContext("2d");
    ctx.clearRect(0, 0, routeOverlay.width, routeOverlay.height);
    ctx.strokeStyle = "#e74c3c";
    ctx.lineWidth = 5;
    ctx.beginPath();

    // Draw route on canvas
    ctx.moveTo(path[0].x, path[0].y);
    path.forEach((point) => ctx.lineTo(point.x, point.y));
    ctx.stroke();

    // Ensure the arrow marker exists
    let arrowMarker = document.getElementById("arrow-marker");
    if (!arrowMarker) {
        arrowMarker = document.createElement("div");
        arrowMarker.id = "arrow-marker";
        arrowMarker.classList.add("arrow-marker");
        document.body.appendChild(arrowMarker);
    }

    // Animate arrow along the path
    let index = 0;
    function moveArrow() {
        if (index >= path.length) return;

        const point = path[index];
        updateMarker(arrowMarker, point.x, point.y);

        // Rotate arrow to face next step
        if (index < path.length - 1) {
            const nextPoint = path[index + 1];
            const angle = Math.atan2(nextPoint.y - point.y, nextPoint.x - point.x) * (180 / Math.PI);
            arrowMarker.style.transform = `rotate(${angle}deg)`;
        }

        index++;
        if (index < path.length) {
            requestAnimationFrame(moveArrow);
        }
    }

    moveArrow();
}



// Update marker position (modify this to apply to arrow marker)
function updateMarker(marker, x, y) {
    const floorPlan = document.getElementById("floor-plan-img");
    const rect = floorPlan.getBoundingClientRect();

    // Adjust marker position to align correctly with the floor plan
    marker.style.left = `${rect.left + x}px`;
    marker.style.top = `${rect.top + y}px`;
    marker.style.display = "block";

    console.log(`Arrow moved to: (${x}, ${y})`);
}




// Find shortest path using BFS
function findPath(start, end) {
    if (!start || !end) {
        console.error("Invalid start or end location:", start, end);
        return null;
    }

    console.log("Finding path from", start.name, "to", end.name);

    const queue = [start.id];
    const visited = new Set();
    const parentMap = new Map();
    
    visited.add(start.id);
    
    while (queue.length > 0) {
        const currentId = queue.shift();
        const current = locations.find(loc => loc.id === currentId);
        
        if (!current) {
            console.warn("Location not found for ID:", currentId);
            continue;
        }

        console.log("Visiting:", current.name, "Connections:", current.connections);

        if (current.id === end.id) {
            return reconstructPath(start, end, parentMap);
        }

        for (const neighborId of current.connections) {
            if (!visited.has(neighborId)) {
                visited.add(neighborId);
                parentMap.set(neighborId, currentId);
                queue.push(neighborId);
            }
        }
    }

    console.error("No path found from", start.name, "to", end.name);
    return null;
}


// Reconstruct path
function reconstructPath(start, end, parentMap) {
    let path = [];
    let currentId = end.id;

    while (parentMap.has(currentId)) {
        path.unshift(locations.find(loc => loc.id === currentId));
        currentId = parentMap.get(currentId);
    }

    path.unshift(start);
    return path;
}

// Initialize on page load
document.addEventListener('DOMContentLoaded', initialize);
