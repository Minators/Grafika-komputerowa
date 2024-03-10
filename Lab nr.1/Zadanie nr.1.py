import pygame
import math

pygame.init()
win = pygame.display.set_mode((600, 600))
pygame.display.set_caption("First Game")

# Wymiary okna gry
width = 600
height = 600

# Deklarowanie koloru
RED = (255, 0, 0)
YELLOW = (255, 214, 77)

# Liczba wierzchołków trzynastokąta
num_vertices = 13

# Promień trzynastokąta
radius = 150

# Tworzenie trzynastokąta
def calculate_vertices(radius, angle_offset):
    vertices = []
    for i in range(num_vertices):
        angle = i * (2 * math.pi) / num_vertices + angle_offset
        x = int(width / 2 + radius * math.cos(angle))
        y = int(height / 2 + radius * math.sin(angle))
        vertices.append((x, y))
    return vertices

# Odbicie lustrzane w pionie
def reflect_vertical(vertices):
    reflected_vertices = [(x, height - y) for x, y in vertices]
    return reflected_vertices

# ściśnięcie pionowo o połowę
def compress_vertical(vertices):
    center_x = sum(x for x, _ in vertices) / len(vertices)
    center_y = sum(y for _, y in vertices) / len(vertices)
    compressed_vertices = [(x, (y - center_y) * 0.5 + center_y) for x, y in vertices]
    return compressed_vertices

# ściśnięcie poziome o połowę
def compress_horizontal(vertices):
    center_x = sum(x for x, _ in vertices) / len(vertices)
    center_y = sum(y for _, y in vertices) / len(vertices)
    compressed_vertices = [((x - center_x) * 0.5 + center_x, y) for x, y in vertices]
    return compressed_vertices

# Przeniesienie do górnej krawędzi okna
def move_to_top(vertices):
    min_y = min(y for _, y in vertices)  # Znajdź minimalną wartość y
    offset = min_y  # Oblicz o ile trzeba przesunąć wierzchołki w górę
    moved_vertices = [(x, y - offset) for x, y in vertices]  # Przesuń wierzchołki
    return moved_vertices

# Przeniesieniedo prawej krawędzi okna
def move_to_right(vertices):
    max_x = max(x for x, _ in vertices)  # Znajdź maksymalną wartość x
    offset = width - max_x  # Oblicz o ile trzeba przesunąć wierzchołki w prawo
    moved_vertices = [(x + offset, y) for x, y in vertices]  # Przesuń wierzchołki
    return moved_vertices

# Pierwotne wierzchołki
initial_vertices = calculate_vertices(radius, 0)

angle_offset = 0

# Kopia pierwotnych wierzchołków trzynastokąta
vertices = initial_vertices[:]

run = True

# Zmienna do śledzenia ostatniego naciśniętego klawisza
last_key_pressed = None 

while run:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            run = False
        elif event.type == pygame.KEYDOWN:
            if last_key_pressed != pygame.K_1:
                if event.key == pygame.K_1:
                    radius = 150
                    vertices = initial_vertices[:] # Powrót do pierwotnych wierzchołków
                    radius *= 0.6 # Pomniejszenie o 40%
                    vertices = calculate_vertices(radius, angle_offset) # Oblicza nowe wierzchołki
                    last_key_pressed = pygame.K_1
            if last_key_pressed != pygame.K_2:
                if event.key == pygame.K_2:
                    radius = 150
                    vertices = initial_vertices[:] # Powrót do pierwotnych wierzchołków
                    last_key_pressed = pygame.K_2
            if last_key_pressed != pygame.K_3:
                if event.key == pygame.K_3:
                    radius = 150
                    vertices = initial_vertices[:] # Powrót do pierwotnych wierzchołków
                    angle_offset += math.radians(-180) # obrót o 180 stopni
                    vertices = calculate_vertices(radius, angle_offset)
                    vertices = compress_horizontal(vertices)
                    vertices = reflect_vertical(vertices)
                    last_key_pressed = pygame.K_3
            if last_key_pressed != pygame.K_4:
                if event.key == pygame.K_4:
                    radius = 150
                    vertices = initial_vertices[:] # Powrót do pierwotnych wierzchołków
                    for i in range(len(vertices)):
                        x, y = vertices[i]
                        vertices[i] = (x + (y - height / 2) * 0.55, y)
                    last_key_pressed = pygame.K_4
            if last_key_pressed != pygame.K_5:
                if event.key == pygame.K_5:
                    radius = 150
                    vertices = initial_vertices[:] # Powrót do pierwotnych wierzchołków
                    vertices = compress_vertical(vertices)
                    vertices = move_to_top(vertices)
                    last_key_pressed = pygame.K_5
            if last_key_pressed != pygame.K_6:
                if event.key == pygame.K_6:
                    radius = 150
                    vertices = initial_vertices[:] # Powrót do pierwotnych wierzchołków
                    for i in range(len(vertices)):
                        x, y = vertices[i]
                        vertices[i] = (x + (y - height / 2) * 0.55, y)
                    for i in range(len(vertices)):
                        x, y = vertices[i]
                        vertices[i] = (height / 2 - (y - height / 2) * 0.55, x)
                    last_key_pressed = pygame.K_6
            if last_key_pressed != pygame.K_7:
                if event.key == pygame.K_7:
                    radius = 150
                    vertices = initial_vertices[:] # Powrót do pierwotnych wierzchołków
                    angle_offset += math.radians(90)
                    vertices = calculate_vertices(radius, angle_offset)
                    vertices = compress_horizontal(vertices)
                    last_key_pressed = pygame.K_7
            if last_key_pressed != pygame.K_8:
                if event.key == pygame.K_8:
                    radius = 150
                    vertices = initial_vertices[:] # Powrót do pierwotnych wierzchołków
                    vertices = compress_vertical(vertices)
                    for i in range(len(vertices)):
                        x, y = vertices[i]
                        new_x = (x - width / 2) * math.cos(math.radians(22.5)) - (y - height / 2) * math.sin(math.radians(22.5)) + width / 2
                        new_y = (x - width / 2) * math.sin(math.radians(22.5)) + (y - height / 2) * math.cos(math.radians(22.5)) + height / 2
                        vertices[i] = (new_x, new_y)
                    for i in range(len(vertices)):
                        x, y = vertices[i]
                        vertices[i] = (x - 100, y + 100)
                    last_key_pressed = pygame.K_8
            if last_key_pressed != pygame.K_9:
                if event.key == pygame.K_9:
                    radius = 150
                    vertices = initial_vertices[:] # Powrót do pierwotnych wierzchołków
                    angle_offset += math.radians(180)
                    vertices = calculate_vertices(radius, angle_offset)
                    for i in range(len(vertices)):
                        x, y = vertices[i]
                        vertices[i] = (x + (y - height / 2) * 0.55, y)
                    vertices = move_to_right(vertices)
                    last_key_pressed = pygame.K_9

    win.fill(YELLOW) # Zmiana koloru tła

    pygame.draw.polygon(win, RED, vertices) 

    pygame.display.update()

pygame.quit()