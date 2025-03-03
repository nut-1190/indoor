// Indoor Navigation System - Main Application Logic

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
const currentPositionText = document.getElementById('current-position-text');
const destinationDropdown = document.getElementById('destination-dropdown');
const navigateBtn = document.getElementById('navigate-btn');
const scanQrBtn = document.getElementById('scan-qr');
const qrScannerModal = document.getElementById('qr-scanner-modal');
const closeBtn = document.querySelector('.close-btn');
const directionsList = document.getElementById('directions-list');

// Initialize the application
async function initialize() {
    try {
        // Load location data
        const response = await fetch('data/locations.json');
        const data = await response.json();
        locations = data.locations;
        
        // Populate destination dropdown
        populateDestinationDropdown();
        
        // Set up event listeners
        setupEventListeners();
        
        // Initialize canvas
        setupCanvas();
        
        console.log('Application initialized successfully');
    } catch (error) {
        console.error('Failed to initialize application:', error);
    }
}

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

// Populate the destination dropdown with locations
function populateDestinationDropdown() {
    // Clear existing options
    while (destinationDropdown.options.length > 1) {
        destinationDropdown.remove(1);
    }
    
    // Add locations to dropdown
    locations.forEach(location => {
        const option = document.createElement('option');
        option.value = location.id;
        option.textContent = location.name;
        destinationDropdown.appendChild(option);
    });
}

// Set up event listeners
function setupEventListeners() {
    // Scan QR code button
    scanQrBtn.addEventListener('click', openQrScanner);
    
    // Close QR scanner modal
    closeBtn.addEventListener('click', closeQrScanner);
    
    // Navigate button
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
    
    // Destination selection change
    destinationDropdown.addEventListener('change', () => {
        navigateBtn.disabled = !destinationDropdown.value || !currentLocation;
    });
}

// Open the QR scanner modal
function openQrScanner() {
    qrScannerModal.classList.remove('hidden');
    
    // Initialize QR scanner
    const scannerArea = document.getElementById('scanner-area');
    
    html5QrCode = new Html5Qrcode('scanner-area');
    
    const config = { fps: 10, qrbox: { width: 250, height: 250 } };
    
    html5QrCode.start(
        { facingMode: "environment" },
        config,
        onQrCodeSuccess,
        onQrCodeError
    ).catch(err => {
        console.error('QR Scanner failed to start:', err);
    });
}

// Close the QR scanner modal
function closeQrScanner() {
    qrScannerModal.classList.add('hidden');
    
    // Stop the scanner
    if (html5QrCode && html5QrCode.isScanning) {
        html5QrCode.stop().catch(err => {
            console.error('Error stopping QR scanner:', err);
        });
    }
}

// Handle successful QR code scan
function onQrCodeSuccess(qrCodeMessage) {
    console.log('QR Code detected:', qrCodeMessage);
    
    // Find the location based on QR code
    const scannedLocation = locations.find(loc => loc.id === qrCodeMessage);
    
    if (scannedLocation) {
        currentLocation = scannedLocation;
        currentPositionText.textContent = scannedLocation.name;
        
        // Update current position marker
        updateMarker(currentMarker, scannedLocation.x, scannedLocation.y);
        
        // Enable navigation if destination selected
        navigateBtn.disabled = !destinationDropdown.value;
        
        // Close scanner
        closeQrScanner();
        
        // If destination already selected, update route
        if (destinationLocation) {
            drawRoute(currentLocation, destinationLocation);
            generateDirections(currentLocation, destinationLocation);
        }
    } else {
        alert('Unknown location code: ' + qrCodeMessage);
    }
}

// Handle QR code scan errors
function onQrCodeError(err) {
    // Just log errors, don't display to user unless it's critical
    console.error('QR Code error:', err);
}

// Update a marker position and make it visible
function updateMarker(marker, x, y) {
    // Calculate position based on image scale
    const scaleX = floorPlanImg.clientWidth / floorPlanImg.naturalWidth;
    const scaleY = floorPlanImg.clientHeight / floorPlanImg.naturalHeight;
    
    marker.style.left = `${x * scaleX}px`;
    marker.style.top = `${y * scaleY}px`;
    marker.style.display = 'block';
}

// Draw a route between two locations
function drawRoute(start, end) {
    // Find path using BFS
    const path = findPath(start, end);
    
    if (!path || path.length < 2) {
        console.error('No valid path found');
        return;
    }
    
    // Get canvas context
    const ctx = routeOverlay.getContext('2d');
    ctx.clearRect(0, 0, routeOverlay.width, routeOverlay.height);
    
    // Calculate scale factors
    const scaleX = floorPlanImg.clientWidth / floorPlanImg.naturalWidth;
    const scaleY = floorPlanImg.clientHeight / floorPlanImg.naturalHeight;
    
    // Set line style
    ctx.strokeStyle = '#e74c3c';
    ctx.lineWidth = 5;
    ctx.lineCap = 'round';
    ctx.lineJoin = 'round';
    
    // Draw path
    ctx.beginPath();
    ctx.moveTo(path[0].x * scaleX, path[0].y * scaleY);
    
    for (let i = 1; i < path.length; i++) {
        ctx.lineTo(path[i].x * scaleX, path[i].y * scaleY);
    }
    
    ctx.stroke();
    
    // Update destination marker
    updateMarker(destinationMarker, end.x, end.y);
}

// Find path between two locations using BFS
function findPath(start, end) {
    // Queue for BFS
    const queue = [];
    // Set to track visited nodes
    const visited = new Set();
    // Map to store parent nodes for path reconstruction
    const parentMap = new Map();
    
    // Start BFS from the start location
    queue.push(start);
    visited.add(start.id);
    
    while (queue.length > 0) {
        const current = queue.shift();
        
        // If we reached the destination
        if (current.id === end.id) {
            // Reconstruct path
            return reconstructPath(start, end, parentMap);
        }
        
        // Process all connections
        for (const connId of current.connections) {
            const connectedLoc = locations.find(loc => loc.id === connId);
            
            if (connectedLoc && !visited.has(connectedLoc.id)) {
                visited.add(connectedLoc.id);
                queue.push(connectedLoc);
                parentMap.set(connectedLoc.id, current);
            }
        }
    }
    
    // No path found
    return null;
}

// Reconstruct path from parentMap
function reconstructPath(start, end, parentMap) {
    const path = [end];
    let current = end;
    
    while (current.id !== start.id) {
        current = parentMap.get(current.id);
        if (!current) break;
        path.unshift(current);
    }
    
    return path;
}

// Generate text directions
function generateDirections(start, end) {
    // Clear existing directions
    directionsList.innerHTML = '';
    
    // Find path
    const path = findPath(start, end);
    
    if (!path || path.length < 2) {
        const li = document.createElement('li');
        li.textContent = 'No valid path found.';
        directionsList.appendChild(li);
        return;
    }
    
    // Generate directions based on path
    let currentDirection = '';
    let distance = 0;
    let steps = [];
    
    for (let i = 1; i < path.length; i++) {
        const prev = path[i-1];
        const current = path[i];
        
        // Calculate direction
        const dx = current.x - prev.x;
        const dy = current.y - prev.y;
        let direction = '';
        
        if (Math.abs(dx) > Math.abs(dy)) {
            direction = dx > 0 ? 'east' : 'west';
        } else {
            direction = dy > 0 ? 'south' : 'north';
        }
        
        // Calculate approximate distance
        const segmentDistance = Math.sqrt(dx*dx + dy*dy);
        
        // If continuing in same direction, add distance
        if (direction === currentDirection) {
            distance += segmentDistance;
        } else {
            // Add previous step if exists
            if (currentDirection) {
                steps.push(`Continue ${Math.round(distance)} units ${currentDirection}`);
            }
            
            // Start new direction
            currentDirection = direction;
            distance = segmentDistance;
        }
        
        // If reached a named location (not a hallway junction)
        if (current.type !== 'hallway' || i === path.length - 1) {
            steps.push(`Go ${Math.round(distance)} units ${currentDirection} to reach ${current.name}`);
            currentDirection = '';
            distance = 0;
        }
    }
    
    // Add steps to directions list
    steps.forEach(step => {
        const li = document.createElement('li');
        li.textContent = step;
        directionsList.appendChild(li);
    });
}

// Initialize app when DOM is loaded
document.addEventListener('DOMContentLoaded', initialize);