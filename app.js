// Indoor Navigation System - Main Application Logic

let locations = [];
let currentLocation = null;
let destinationLocation = null;
let html5QrCode = null;

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

async function initialize() {
    try {
        const response = await fetch('data/locations.json');
        const data = await response.json();
        locations = data.locations;

        populateDestinationDropdown();
        setupEventListeners();
        setupCanvas();

        console.log('Application initialized successfully');
    } catch (error) {
        console.error('Failed to initialize application:', error);
    }
}

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

function setupEventListeners() {
    scanQrBtn.addEventListener('click', openQrScanner);
    closeBtn.addEventListener('click', closeQrScanner);

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

    destinationDropdown.addEventListener('change', () => {
        navigateBtn.disabled = !destinationDropdown.value || !currentLocation;
    });
}

function openQrScanner() {
    qrScannerModal.classList.remove('hidden');

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

function closeQrScanner() {
    qrScannerModal.classList.add('hidden');

    if (html5QrCode && html5QrCode.isScanning) {
        html5QrCode.stop().catch(err => {
            console.error('Error stopping QR scanner:', err);
        });
    }
}

function onQrCodeSuccess(qrCodeMessage) {
    console.log('QR Code detected:', qrCodeMessage);

    const scannedLocation = locations.find(loc => loc.id === qrCodeMessage);

    if (scannedLocation) {
        currentLocation = scannedLocation;
        currentPositionText.textContent = scannedLocation.name;

        updateMarker(currentMarker, scannedLocation.x, scannedLocation.y);
        navigateBtn.disabled = !destinationDropdown.value;
        closeQrScanner();

        if (destinationLocation) {
            drawRoute(currentLocation, destinationLocation);
            generateDirections(currentLocation, destinationLocation);
        }
    } else {
        alert('Unknown location code: ' + qrCodeMessage);
    }
}

function onQrCodeError(err) {
    console.error('QR Code error:', err);
}

function updateMarker(marker, x, y) {
    const scaleX = floorPlanImg.clientWidth / floorPlanImg.naturalWidth;
    const scaleY = floorPlanImg.clientHeight / floorPlanImg.naturalHeight;

    marker.style.left = `${x * scaleX}px`;
    marker.style.top = `${y * scaleY}px`;
    marker.style.display = 'block';
}

function drawRoute(start, end) {
    const path = findPath(start, end);

    if (!path || path.length < 2) {
        console.error('No valid path found');
        return;
    }

    const ctx = routeOverlay.getContext('2d');
    ctx.clearRect(0, 0, routeOverlay.width, routeOverlay.height);

    const scaleX = floorPlanImg.clientWidth / floorPlanImg.naturalWidth;
    const scaleY = floorPlanImg.clientHeight / floorPlanImg.naturalHeight;

    ctx.strokeStyle = '#e74c3c';
    ctx.lineWidth = 5;
    ctx.beginPath();
    ctx.moveTo(path[0].x * scaleX, path[0].y * scaleY);

    for (let i = 1; i < path.length; i++) {
        ctx.lineTo(path[i].x * scaleX, path[i].y * scaleY);
    }

    ctx.stroke();
    updateMarker(destinationMarker, end.x, end.y);
}

document.addEventListener('DOMContentLoaded', initialize);
