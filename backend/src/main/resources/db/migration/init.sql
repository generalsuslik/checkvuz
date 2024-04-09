insert into check_vuz.user_roles (title)
values
('ROLE_GUEST'), ('ROLE_USER'), ('ROLE_STUFF'), ('ROLE_ADMIN');

INSERT INTO check_vuz.users (username, email, password)
VALUES
    ('admin','admin@gmail.com', '$2a$12$SRysoj/uuMog/j1eAPCPN.RAvluRPwRVccfVIz.rcj0IKW3XDZLmy'),
    ('stuff', 'stuff@gmal.com', '$2a$12$EnDeXiZlm5PLnPMVSb1MpexCWC//Cw.5A.Vy49ZoHPpdthdgZLJhG');

-- admin
INSERT INTO check_vuz.user_roles_relation (user_id, user_role_id)
VALUES
    ((SELECT id FROM check_vuz.users WHERE username = 'admin'), 4);

INSERT INTO check_vuz.user_roles_relation (user_id, user_role_id)
VALUES
    ((SELECT id FROM check_vuz.users WHERE username = 'admin'), 3);

INSERT INTO check_vuz.user_roles_relation (user_id, user_role_id)
VALUES
    ((SELECT id FROM check_vuz.users WHERE username = 'admin'), 2);
-- admin

-- stuff
INSERT INTO check_vuz.user_roles_relation (user_id, user_role_id)
VALUES
    ((SELECT id FROM check_vuz.users WHERE username = 'stuff'), 3);

INSERT INTO check_vuz.user_roles_relation (user_id, user_role_id)
VALUES
    ((SELECT id FROM check_vuz.users WHERE username = 'stuff'), 2);

-- stuff

-- default user image insertion
insert into check_vuz.images (created_at, image_path, image_url, title, type, is_default)
values (
           '2024-04-09 15:59:27.193000',
           '/home/generalsuslik/Projects/check_vuz/backend/src/main/resources/static/images/default/users/default_user_image.jpg',
           'http://localhost:8080/api/images/default_user_image.jpg',
           'default_user_image.jpg',
           'image/jpg',
           true
       )
