import os

def list_structure(root_dir, file_out):
    with open(file_out, "w", encoding="utf-8") as f:
        for root, dirs, files in os.walk(root_dir):
            level = root.replace(root_dir, "").count(os.sep)
            indent = " " * 4 * level
            f.write(f"{indent}{os.path.basename(root)}/\n")
            subindent = " " * 4 * (level + 1)
            for file in files:
                f.write(f"{subindent}{file}\n")

if __name__ == "__main__":
    root_path = os.getcwd()
    list_structure(root_path, "project_structure.txt")
    print("? Xu?t xong -> file project_structure.txt")
