import os
import json

def generate_models_and_blockstates():
    pipes = ["unrouted_pipe", "basic_pipe", "extraction_pipe", "insertion_pipe"]
    
    # Paths
    base_dir = "common/src/main/resources/assets/logisticsrepiped"
    blockstates_dir = os.path.join(base_dir, "blockstates")
    models_block_dir = os.path.join(base_dir, "models", "block")
    models_item_dir = os.path.join(base_dir, "models", "item")
    
    os.makedirs(blockstates_dir, exist_ok=True)
    os.makedirs(models_block_dir, exist_ok=True)
    os.makedirs(models_item_dir, exist_ok=True)
    
    core_template = {
        "parent": "minecraft:block/block",
        "render_type": "minecraft:cutout",
        "textures": {
            "particle": "#pipe"
        },
        "elements": [
            {
                "from": [5, 5, 5],
                "to": [11, 11, 11],
                "faces": {
                    "north": {"uv": [0, 0, 16, 16], "texture": "#pipe"},
                    "east":  {"uv": [0, 0, 16, 16], "texture": "#pipe"},
                    "south": {"uv": [0, 0, 16, 16], "texture": "#pipe"},
                    "west":  {"uv": [0, 0, 16, 16], "texture": "#pipe"},
                    "up":    {"uv": [0, 0, 16, 16], "texture": "#pipe"},
                    "down":  {"uv": [0, 0, 16, 16], "texture": "#pipe"}
                }
            }
        ]
    }

    extension_template = {
        "parent": "minecraft:block/block",
        "render_type": "minecraft:cutout",
        "textures": {
            "particle": "#pipe"
        },
        "elements": [
            {
                "from": [5, 5, 0],
                "to": [11, 11, 5],
                "faces": {
                    "north": {"uv": [0, 0, 16, 16], "texture": "#pipe"},
                    "east":  {"uv": [0, 0, 16, 16], "texture": "#pipe"},
                    "south": {"uv": [0, 0, 16, 16], "texture": "#pipe"},
                    "west":  {"uv": [0, 0, 16, 16], "texture": "#pipe"},
                    "up":    {"uv": [0, 0, 16, 16], "texture": "#pipe"},
                    "down":  {"uv": [0, 0, 16, 16], "texture": "#pipe"}
                }
            }
        ]
    }
    
    with open(os.path.join(models_block_dir, "pipe_core_template.json"), "w") as f:
        json.dump(core_template, f, indent=4)
        
    with open(os.path.join(models_block_dir, "pipe_extension_template.json"), "w") as f:
        json.dump(extension_template, f, indent=4)

    for pipe in pipes:
        # Block Models
        core_model = {
            "parent": "logisticsrepiped:block/pipe_core_template",
            "textures": {
                "pipe": f"logisticsrepiped:block/{pipe}"
            }
        }
        with open(os.path.join(models_block_dir, f"{pipe}_core.json"), "w") as f:
            json.dump(core_model, f, indent=4)

        ext_model = {
            "parent": "logisticsrepiped:block/pipe_extension_template",
            "textures": {
                "pipe": f"logisticsrepiped:block/{pipe}"
            }
        }
        with open(os.path.join(models_block_dir, f"{pipe}_extension.json"), "w") as f:
            json.dump(ext_model, f, indent=4)

        # Blockstate
        # We need multipart for 6 connections.
        blockstate = {
            "multipart": [
                {
                    "apply": { "model": f"logisticsrepiped:block/{pipe}_core" }
                },
                {
                    "when": { "north": "true" },
                    "apply": { "model": f"logisticsrepiped:block/{pipe}_extension" }
                },
                {
                    "when": { "east": "true" },
                    "apply": { "model": f"logisticsrepiped:block/{pipe}_extension", "y": 90 }
                },
                {
                    "when": { "south": "true" },
                    "apply": { "model": f"logisticsrepiped:block/{pipe}_extension", "y": 180 }
                },
                {
                    "when": { "west": "true" },
                    "apply": { "model": f"logisticsrepiped:block/{pipe}_extension", "y": 270 }
                },
                {
                    # For UP, we rotate around X axis so NORTH points UP.
                    # NORTH original is Z- (into page). UP is Y+ (top).
                    # X=90 moves Z- to Y-, X=270 moves Z- to Y+. So x=270.
                    "when": { "up": "true" },
                    "apply": { "model": f"logisticsrepiped:block/{pipe}_extension", "x": 270 }
                },
                {
                    # DOWN is Y-. X=90 moves Z- to Y-.
                    "when": { "down": "true" },
                    "apply": { "model": f"logisticsrepiped:block/{pipe}_extension", "x": 90 }
                }
            ]
        }
        with open(os.path.join(blockstates_dir, f"{pipe}.json"), "w") as f:
            json.dump(blockstate, f, indent=4)
            
        # Item model (just showing a core block in inventory)
        item_model = {
            "parent": f"logisticsrepiped:block/{pipe}_core"
        }
        with open(os.path.join(models_item_dir, f"{pipe}.json"), "w") as f:
            json.dump(item_model, f, indent=4)

if __name__ == "__main__":
    generate_models_and_blockstates()
    print("Models and blockstates generated.")
