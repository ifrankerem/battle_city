from PIL import Image
img = Image.open('resources/images/spritesheet2.png')
chars = ' .:-=+*#%@'
def print_region(x, y, w, h):
    for j in range(h):
        line = ''
        for i in range(w):
            p = img.getpixel((x+i, y+j))
            brightness = sum(p[:3])/3
            line += chars[int(brightness/256 * len(chars))]
        print(line)
for i in range(6):
    print(f'Powerup {i} at X={256 + i*16}, Y=112:')
    print_region(256 + i*16, 112, 16, 16)
