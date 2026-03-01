import os
from PIL import Image, ImageDraw

def create_frame_texture(color_hex, filename):
    # Create a 16x16 transparent image
    img = Image.new('RGBA', (16, 16), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    
    # Parse hex color
    h = color_hex.lstrip('#')
    color = tuple(int(h[i:i+2], 16) for i in (0, 2, 4)) + (255,)
    
    # Draw 1-pixel frame (borders)
    # Top
    draw.rectangle([0, 0, 15, 0], fill=color)
    # Bottom
    draw.rectangle([0, 15, 15, 15], fill=color)
    # Left
    draw.rectangle([0, 0, 0, 15], fill=color)
    # Right
    draw.rectangle([15, 0, 15, 15], fill=color)
    
    # Save image
    img.save(filename)
    print(f"Generated {filename}")

if __name__ == "__main__":
    # Ensure directory exists
    output_dir = "common/src/main/resources/assets/logisticsrepiped/textures/block"
    os.makedirs(output_dir, exist_ok=True)
    
    textures = {
        "unrouted_pipe": "#808080",   # Gray
        "basic_pipe": "#00FF00",      # Green
        "extraction_pipe": "#FF0000", # Red
        "insertion_pipe": "#0000FF",  # Blue
    }
    
    for name, hex_color in textures.items():
        filename = os.path.join(output_dir, f"{name}.png")
        create_frame_texture(hex_color, filename)
    print("All textures generated successfully.")
