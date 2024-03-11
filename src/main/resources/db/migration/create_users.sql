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


