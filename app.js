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

// Set up the canvas for route drawing
function setupCanvas() {
    const canvas = routeOverlay;
    canvas.width = floorPlanImg.width;
    canvas.height = floorPlanImg.height;

    // Update canvas size when image loads
    floorPlanImg.onload = () => {
        canvas.width = floorPlanImg.clientWidth;
        canvas.height = floorPlanImg.clientHeight;
    };

    // Handle window resize
    window.addEventListener('resize', () => {
        canvas.width = floorPlanImg.clientWidth;
        canvas.height = floorPlanImg.clientHeight;

        // Redraw route if needed
        if (currentLocation && destinationLocation) {
            drawRoute(currentLocation, destinationLocation);
        }
    });
}

// Now define initialize(), which will call setupCanvas()
function initialize() {
    setupCanvas(); // Call it here
    setupEventListeners();
}

// Initialize the application
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
        
        console.log('Application initialized successfully');
    } catch (error) {
        console.error('Failed to initialize application:', error);
    }
}

// Populate source dropdown
function populateSourceDropdown() {
    while (sourceDropdown.options.length > 1) {
        sourceDropdown.remove(1);
    }

    locations.forEach(location => {
        const option = document.createElement('option');
        option.value = location.id;
        option.textContent = location.name;
        sourceDropdown.appendChild(option);
    });
}

// Populate destination dropdown
function populateDestinationDropdown() {
    while (destinationDropdown.options.length > 1) {
        destinationDropdown.remove(1);
    }

    locations.forEach(location => {
        const option = document.createElement('option');
        option.value = location.id;
        option.textContent = location.name;
        destinationDropdown.appendChild(option);
    });
}
// Generate step-by-step directions
// Generate step-by-step directions from the path
function generateDirections(path) {
    const directionsList = document.getElementById('directions-list');
    directionsList.innerHTML = '';

    if (!path || path.length === 0) {
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
    // Handle manual source selection
    sourceDropdown.addEventListener('change', function () {
        const selectedId = this.value;
        if (selectedId) {
            currentLocation = locations.find(loc => loc.id === selectedId);
            updateMarker(currentMarker, currentLocation.x, currentLocation.y);
            navigateBtn.disabled = !destinationDropdown.value;
        }
    });

    // Handle manual destination selection
    destinationDropdown.addEventListener('change', function () {
        navigateBtn.disabled = !destinationDropdown.value || !currentLocation;
    });

    // Handle navigation
    navigateBtn.addEventListener('click', () => {
        const selectedId = destinationDropdown.value;
        if (selectedId && currentLocation) {
            destinationLocation = locations.find(loc => loc.id === selectedId);
            if (destinationLocation) {
                drawRoute(currentLocation, destinationLocation);
                generateDirections(currentLocation, destinationLocation);
            }
        }
    });

    // QR Scanner button
    scanQrBtn.addEventListener('click', openQrScanner);
    closeBtn.addEventListener('click', closeQrScanner);
}

// Open QR Scanner
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

// Close QR Scanner
function closeQrScanner() {
    qrScannerModal.classList.add('hidden');
    if (html5QrCode && html5QrCode.isScanning) {
        html5QrCode.stop().catch(err => console.error('Error stopping QR scanner:', err));
    }
}

// Handle QR code scan
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
function updateMarker(marker, x, y) {
    const scaleX = floorPlanImg.clientWidth / floorPlanImg.naturalWidth;
    const scaleY = floorPlanImg.clientHeight / floorPlanImg.naturalHeight;
    marker.style.left = `${x * scaleX}px`;
    marker.style.top = `${y * scaleY}px`;
    marker.style.display = 'block';
}

// Draw route between locations
function drawRoute(start, end) {
    const path = findPath(start, end);
    if (!path || path.length < 2) {
        console.error('No path found');
        return;
    }

    const ctx = routeOverlay.getContext('2d');
    ctx.clearRect(0, 0, routeOverlay.width, routeOverlay.height);
    ctx.strokeStyle = '#e74c3c';
    ctx.lineWidth = 5;
    ctx.beginPath();
    
    ctx.moveTo(path[0].x, path[0].y);
    path.forEach(point => ctx.lineTo(point.x, point.y));
    
    ctx.stroke();
    updateMarker(destinationMarker, end.x, end.y);
}
// Reconstruct the shortest path from the parent map
function reconstructPath(start, end, parentMap) {
    const path = [];
    let current = end;

    while (current.id !== start.id) {
        path.unshift(current);
        current = parentMap.get(current.id);
        if (!current) return null;  // Handle broken paths
    }

    path.unshift(start); // Include the start node
    return path;
}

// Now define findPath(), which uses reconstructPath()
function findPath(start, end) {
    const queue = [start];
    const visited = new Set();
    const parentMap = new Map();
    
    visited.add(start.id);
    
    while (queue.length > 0) {
        const current = queue.shift();

        if (current.id === end.id) {
            return reconstructPath(start, end, parentMap);
        }

        for (const neighbor of current.neighbors) {
            if (!visited.has(neighbor.id)) {
                visited.add(neighbor.id);
                parentMap.set(neighbor.id, current);
                queue.push(neighbor);
            }
        }
    }

    return null; // No path found
}


// Find shortest path (using BFS)
function findPath(start, end) {
    let queue = [start];
    let visited = new Set([start.id]);
    let parentMap = new Map();

    while (queue.length) {
        let current = queue.shift();
        if (current.id === end.id) return reconstructPath(start, end, parentMap);
        
        current.connections.forEach(connId => {
            let next = locations.find(loc => loc.id === connId);
            if (next && !visited.has(next.id)) {
                visited.add(next.id);
                queue.push(next);
                parentMap.set(next.id, current);
            }
        });
    }
    return null;
}

document.addEventListener('DOMContentLoaded', initialize);
