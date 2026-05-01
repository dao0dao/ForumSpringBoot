INSERT INTO users_roles (user_id, role_id)
VALUES 
    ((SELECT id FROM users WHERE username = 'superadmin'), 
     (SELECT id FROM roles WHERE name = 'SUPER_ADMIN')),
    
    ((SELECT id FROM users WHERE username = 'admin'), 
     (SELECT id FROM roles WHERE name = 'ADMIN')),
    
    ((SELECT id FROM users WHERE username = 'user'), 
     (SELECT id FROM roles WHERE name = 'USER'))
    ON CONFLICT (user_id, role_id) DO NOTHING;