import os
from PIL import Image, ImageDraw

def create_wrench_texture(filename):
    # 16x16 transparent image
    img = Image.new('RGBA', (16, 16), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    
    # Draw simple yellow/orange wrench shape
    color = (255, 165, 0, 255) # Orange
    
    # Handle
    draw.line([(3,12), (10,5)], fill=color, width=2)
    
    # Head
    draw.arc([8, 0, 15, 7], start=0, end=360, fill=color, width=2)
    # Cutout
    draw.line([(11,2), (16,-3)], fill=(0,0,0,0), width=4)
    
    img.save(filename)
    print(f"Generated {filename}")

if __name__ == "__main__":
    output_dir = "common/src/main/resources/assets/logisticsrepiped/textures/item"
    os.makedirs(output_dir, exist_ok=True)
    create_wrench_texture(os.path.join(output_dir, "wrench.png"))
