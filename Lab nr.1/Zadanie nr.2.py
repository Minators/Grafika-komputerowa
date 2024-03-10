import pygame

pygame.init()
win = pygame.display.set_mode((600, 600))
pygame.display.set_caption("First Game")

# deklarowanie koloru
ZIELONY = (0, 255, 0)

run = True
while run:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            run = False

    pygame.draw.rect(win, ZIELONY , (200, 200, 200, 100))
    pygame.draw.polygon(win, ZIELONY , [(200, 300), (200, 400), (300, 300)])
    pygame.draw.polygon(win, ZIELONY , [(399, 300), (399, 400), (300, 300)])

    pygame.display.update()