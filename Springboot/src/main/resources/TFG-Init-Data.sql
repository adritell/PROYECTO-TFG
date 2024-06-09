use videogames;

INSERT INTO videojuegos (id, nombre, genero, descripcion, anio_Publicacion, precio, calificacion_por_edades, publicador, image_Path) VALUES
(1, 'Elden Ring', 'Action RPG', 'A fantasy action RPG.', 2022, 59.99, 'Mature', 'FromSoftware', '../../../../../assets/games/elden_ring.jpeg'),
(2, 'Diablo IV', 'Action RPG', 'An action RPG.', 2023, 34.99, 'Mature', 'Blizzard Entertainment', '../../../../../assets/games/diablo.jpg'),
(3, 'DOTA 2', 'MOBA', 'A multiplayer online battle arena game.', 2013, 0, 'Teen', 'Valve Corporation', '../../../../../assets/games/dota2.jpg'),
(4, 'The Witcher 3: Wild Hunt', 'Action RPG', 'An open-world action RPG.', 2015, 29.99, 'Mature', 'CD Projekt', '../../../../../assets/games/The-Witcher-3-Wild-Hunt.jpg'),
(5, 'Cyberpunk 2077', 'Open-world RPG', 'A futuristic open-world RPG.', 2020, 49.99, 'Mature', 'CD Projekt', '../../../../../assets/games/cyberpunk.jpg'),
(6, 'Hades', 'Roguelike', 'A roguelike dungeon crawler.', 2020, 24.99, 'Teen', 'Supergiant Games', '../../../../../assets/games/hades.png'),
(7, 'Among Us', 'Social Deduction', 'A multiplayer social deduction game.', 2018, 4.99, 'Everyone 10+', 'Innersloth', '../../../../../assets/games/among_us.jpeg'),
(8, 'Assassin\'s Creed Valhalla', 'Action-Adventure', 'An open-world action-adventure game.', 2020, 59.99, 'Mature', 'Ubisoft', '../../../../../assets/games/ac_valhalla.jpg'),
(9, 'Minecraft', 'Sandbox', 'A sandbox game.', 2011, 26.95, 'Everyone 10+', 'Mojang Studios', '../../../../../assets/games/minecraft.jpg'),
(10, 'Fortnite', 'Battle Royale', 'A battle royale game.', 2017, 0, 'Teen', 'Epic Games', '../../../../../assets/games/fortnite.jpg'),
(11, 'League of Legends', 'MOBA', 'A multiplayer online battle arena game.', 2009, 0, 'Teen', 'Riot Games', '../../../../../assets/games/league_of_legends.jpg'),
(12, 'Valorant', 'First-person Shooter', 'A tactical first-person shooter.', 2020, 0, 'Teen', 'Riot Games', '../../../../../assets/games/valorant.jpg'),
(13, 'Final Fantasy VII Remake', 'RPG', 'A remake of the classic RPG.', 2020, 59.99, '16+', 'Square Enix', '../../../../../assets/games/ffvii_remake.jpg'),
(14, 'God of War', 'Action-adventure', 'An action-adventure game based on mythology.', 2018, 39.99, '18+', 'Sony Interactive Entertainment', '../../../../../assets/games/gow.jpg'),
(15, 'Ratchet and Clank', 'Action-platformer', 'An action-platformer game.', 2016, 29.99, '10+', 'Sony Interactive Entertainment', '../../../../../assets/games/ratchet_and_clank.jpg'),
(16, 'The Legend of Zelda: Breath of the Wild', 'Action-adventure', 'An open-world action-adventure game.', 2017, 59.99, '12+', 'Nintendo', '../../../../../assets/games/BOTW.jpg'),
(17, 'Super Mario Odyssey', 'Platform', 'A platform game.', 2017, 49.99, '7+', 'Nintendo', '../../../../../assets/games/super_mario_odyssey.jpg'),
(18, 'Red Dead Redemption 2', 'Action-adventure', 'An open-world action-adventure game set in the Wild West.', 2018, 59.99, '18+', 'Rockstar Games', '../../../../../assets/games/rdr2.jpg'),
(19, 'Splatoon 3', 'Third-person shooter', 'A colorful and fast-paced multiplayer shooter.', 2022, 59.99, '10+', 'Nintendo', '../../../../../assets/games/splatoon_3.jpg'),
(20, 'Animal Crossing: New Horizons', 'Simulation', 'A social simulation game where you build and manage your island.', 2020, 59.99, '3+', 'Nintendo', '../../../../../assets/games/animal_crossing.jpg'),
(21, 'Mario Kart 8 Deluxe', 'Racing', 'A kart racing game with various characters and tracks.', 2017, 59.99, '3+', 'Nintendo', '../../../../../assets/games/mario_kart_8.jpg'),
(22, 'Hollow Knight', 'Metroidvania', 'An action-adventure game with exploration and platforming elements.', 2017, 14.99, '10+', 'Team Cherry', '../../../../../assets/games/hollow_knight.jpg'),
(23, 'Stardew Valley', 'Simulation', 'A farming simulation game with RPG elements.', 2016, 14.99, '7+', 'ConcernedApe', '../../../../../assets/games/stardew_valley.jpg'),
(24, 'Sekiro: Shadows Die Twice', 'Action-adventure', 'A challenging action-adventure game set in a fictional Japan.', 2019, 59.99, '18+', 'Activision', '../../../../../assets/games/sekiro.jpg'),
(25, 'Celeste', 'Platformer', 'A challenging platformer game with a touching story.', 2018, 19.99, '10+', 'Matt Makes Games', '../../../../../assets/games/celeste.jpg'),
(26, 'Horizon Zero Dawn', 'Action RPG', 'An open-world action RPG set in a post-apocalyptic world.', 2017, 49.99, '16+', 'Sony Interactive Entertainment', '../../../../../assets/games/horizon_zero_dawn.jpg'),
(27, 'Persona 5', 'RPG', 'A role-playing game where you live a double life as a high school student and a phantom thief.', 2016, 59.99, '16+', 'Atlus', '../../../../../assets/games/persona_5.jpg');
(28, 'Sonic Mania', 'Platformer', 'A 2D platformer featuring Sonic the Hedgehog.', 2017, 19.99, 'Everyone', 'Sega', '../../../../../assets/games/sonic_mania.jpg'),
(29, 'Spyro Reignited Trilogy', 'Platformer', 'A collection of remastered Spyro games.', 2018, 39.99, 'Everyone 10+', 'Activision', '../../../../../assets/games/spyro_reignited_trilogy.jpg'),
(30, 'Kirby and the Forgotten Land', 'Platformer', 'A platformer game with a unique yarn-based art style.', 2022, 39.99, 'Everyone', 'Nintendo', '../../../../../assets/games/kirby_y_la_tierra_olvidada.jpg'),
(31, 'Sonic Forces', 'Platformer', 'A platformer game where Sonic and his friends fight against Dr. Eggman.', 2017, 39.99, 'Everyone 10+', 'Sega', '../../../../../assets/games/sonic_forces.jpg'),
(32, 'Sonic Frontiers', 'Open-world Adventure', 'An open-world adventure game starring Sonic the Hedgehog.', 2022, 59.99, 'Everyone 10+', 'Sega', '../../../../../assets/games/sonic_frontiers.jpg'),
(33, 'Super Smash Bros. Ultimate', 'Fighting', 'A crossover fighting game featuring various Nintendo characters.', 2018, 59.99, 'Everyone 10+', 'Nintendo', '../../../../../assets/games/super_smash_bros_ultimate.jpg'),
(34, 'FIFA 24', 'Sports', 'The latest installment in the FIFA series featuring realistic football gameplay.', 2023, 59.99, 'Everyone', 'Electronic Arts', '../../../../../assets/games/fifa24.jpg'),
(35, 'Call of Duty: Modern Warfare 3', 'First-person Shooter', 'An intense first-person shooter set in a modern warfare setting.', 2011, 29.99, 'Mature', 'Activision', '../../../../../assets/games/call_of_dutty_mw3.jpg'),
(36, 'Ratchet and Clank: Rift Apart', 'Action-platformer', 'An action-platformer featuring dimensional travel and breathtaking graphics.', 2021, 69.99, 'Everyone 10+', 'Sony Interactive Entertainment', '../../../../../assets/games/ratchet_and_clank_rift_apart.jpg'),
(37, 'Spider-Man 2', 'Action-Adventure', 'An action-packed adventure featuring the iconic web-slinging superhero.', 2023, 59.99, 'Teen', 'Insomniac Games', '../../../../../assets/games/spiderman_2.jpg');



-- Asignar a cada juego sus plataformas correspondientes:
-- Elden Ring
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (1, 'PlayStation 4');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (1, 'PlayStation 5');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (1, 'Xbox One');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (1, 'Xbox Series X/S');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (1, 'PC');

-- Diablo IV
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (2, 'PlayStation 4');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (2, 'PlayStation 5');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (2, 'Xbox One');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (2, 'Xbox Series X/S');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (2, 'PC');

-- DOTA 2
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (3, 'PC');

-- The Witcher 3: Wild Hunt
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (4, 'PlayStation 4');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (4, 'PlayStation 5');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (4, 'Xbox One');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (4, 'Xbox Series X/S');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (4, 'Nintendo Switch');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (4, 'PC');

-- Cyberpunk 2077
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (5, 'PlayStation 4');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (5, 'PlayStation 5');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (5, 'Xbox One');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (5, 'Xbox Series X/S');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (5, 'PC');

-- Hades
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (6, 'Nintendo Switch');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (6, 'PC');

-- Among Us
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (7, 'Android');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (7, 'iOS');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (7, 'PC');

-- Assassin's Creed Valhalla
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (8, 'PlayStation 4');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (8, 'PlayStation 5');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (8, 'Xbox One');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (8, 'Xbox Series X/S');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (8, 'PC');

-- Minecraft
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (9, 'PlayStation 4');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (9, 'Xbox One');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (9, 'Nintendo Switch');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (9, 'PC');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (9, 'Mobile');

-- Fortnite
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (10, 'PlayStation 4');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (10, 'PlayStation 5');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (10, 'Xbox One');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (10, 'Xbox Series X/S');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (10, 'Nintendo Switch');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (10, 'PC');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (10, 'Mobile');


-- League of Legends
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (11, 'PC');

-- Valorant
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (12, 'PC');

-- Final Fantasy VII Remake
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (13, 'PlayStation 4');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (13, 'PlayStation 5');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (13, 'Microsoft Windows');

-- God of War
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (14, 'PlayStation 4');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (14, 'Microsoft Windows');

-- Ratchet and Clank
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (15, 'PlayStation 4');

-- The Legend of Zelda: Breath of the Wild
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (16, 'Nintendo Switch');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (16, 'Wii U');

-- Super Mario Odyssey
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (17, 'Nintendo Switch');

-- Red Dead Redemption 2
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (18, 'PlayStation 4');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (18, 'Xbox One');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (18, 'Microsoft Windows');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (18, 'Stadia');

-- Splatoon 3
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (19, 'Nintendo Switch');

-- Animal Crossing: New Horizons
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (20, 'Nintendo Switch');


-- Mario Kart 8 Deluxe
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (21, 'Nintendo Switch');

-- Hollow Knight
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (22, 'Microsoft Windows');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (22, 'macOS');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (22, 'Linux');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (22, 'PlayStation 4');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (22, 'Xbox One');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (22, 'Nintendo Switch');

-- Stardew Valley
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (23, 'Microsoft Windows');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (23, 'macOS');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (23, 'Linux');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (23, 'PlayStation 4');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (23, 'Xbox One');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (23, 'Nintendo Switch');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (23, 'iOS');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (23, 'Android');

-- Sekiro: Shadows Die Twice
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (24, 'PlayStation 4');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (24, 'Xbox One');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (24, 'Microsoft Windows');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (24, 'Stadia');

-- Celeste
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (25, 'Microsoft Windows');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (25, 'macOS');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (25, 'Linux');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (25, 'PlayStation 4');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (25, 'Xbox One');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (25, 'Nintendo Switch');

-- Horizon Zero Dawn
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (26, 'PlayStation 4');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (26, 'Microsoft Windows');

-- Persona 5
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (27, 'PlayStation 4');
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (27, 'PlayStation 3');


-- Sonic Mania
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (28, 'PlayStation 4'),
(28, 'Xbox One'),
(28, 'Nintendo Switch'),
(28, 'Microsoft Windows');

-- Spyro Reignited Trilogy
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (29, 'PlayStation 4'),
(29, 'Xbox One'),
(29, 'Nintendo Switch'),
(29, 'Microsoft Windows');

-- Kirby and the Forgotten Land
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (30, 'Nintendo Switch');

-- Sonic Forces
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (31, 'PlayStation 4'),
(31, 'Xbox One'),
(31, 'Nintendo Switch'),
(31, 'Microsoft Windows');

-- Sonic Frontiers
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (32, 'PlayStation 4'),
(32, 'PlayStation 5'),
(32, 'Xbox One'),
(32, 'Xbox Series X/S'),
(32, 'Nintendo Switch'),
(32, 'Microsoft Windows');

-- Super Smash Bros. Ultimate
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (33, 'Nintendo Switch');

-- FIFA 24
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (34, 'PlayStation 5'),
(34, 'Xbox Series X/S'),
(34, 'PC');

-- Call of Duty: Modern Warfare 3
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (35, 'PlayStation 3'),
(35, 'Xbox 360'),
(35, 'PC');

-- Ratchet and Clank: Rift Apart
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (36, 'PlayStation 5');

-- Spider-Man 2
INSERT INTO videojuego_plataforma (videojuego_id, plataforma) VALUES (37, 'PlayStation 5');

