$(document).ready(function() {
    let isDragging = false;
    let currentElement = null;
    let offsetX, offsetY;

    // Make elements draggable
    $('.draggable').on('mousedown', function(e) {
        isDragging = true;
        currentElement = $(this);
        
        // Calculate offset from mouse to element corner
        const rect = currentElement[0].getBoundingClientRect();
        offsetX = e.clientX - rect.left;
        offsetY = e.clientY - rect.top;
        
        // Bring to front
        $('.draggable').css('z-index', 10);
        currentElement.css('z-index', 20);
        
        e.preventDefault();
    });

    $(document).on('mousemove', function(e) {
        if (!isDragging || !currentElement) return;
        
        const canvas = $('#canvas')[0].getBoundingClientRect();
        const x = e.clientX - canvas.left - offsetX;
        const y = e.clientY - canvas.top - offsetY;
        
        // Constrain to canvas
        const maxX = canvas.width - currentElement.width();
        const maxY = canvas.height - currentElement.height();
        
        const constrainedX = Math.max(0, Math.min(x, maxX));
        const constrainedY = Math.max(0, Math.min(y, maxY));
        
        currentElement.css({
            left: constrainedX + 'px',
            top: constrainedY + 'px'
        });
    });

    $(document).on('mouseup', function() {
        isDragging = false;
        currentElement = null;
    });

    // Add sticker to canvas
    window.addSticker = function(stickerPath) {
        const pageId = window.location.pathname.split('/')[2];
        fetch(`/page/${pageId}/add-sticker`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `stickerPath=${encodeURIComponent(stickerPath)}`
        }).then(response => {
            if (response.ok) {
                window.location.reload();
            }
        });
    };
});

// Save positions to server
function savePositions() {
    const pageId = window.location.pathname.split('/')[2];
    const imagePositions = [];
    const stickerPositions = [];
    
    $('.draggable').each(function() {
        const type = $(this).data('type');
        const index = $(this).data('index');
        const left = parseInt($(this).css('left'));
        const top = parseInt($(this).css('top'));
        
        if (type === 'image') {
            imagePositions[index] = `${left},${top}`;
        } else if (type === 'sticker') {
            stickerPositions[index] = `${left},${top}`;
        }
    });
    
    fetch(`/page/${pageId}/save-positions`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `imagePositions=${encodeURIComponent(imagePositions.join('|'))}&stickerPositions=${encodeURIComponent(stickerPositions.join('|'))}`
    }).then(response => {
        if (response.ok) {
            alert('Layout saved successfully!');
        }
    });
}