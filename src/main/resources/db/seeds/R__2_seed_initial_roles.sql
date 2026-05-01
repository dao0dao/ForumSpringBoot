INSERT INTO
    roles (
        name,
        user_system_name,
        created_by
    )
VALUES (
        'SUPER_ADMIN',
        'SUPER_ADMIN',
        'SUPER_ADMIN'
    ),
    (
        'ADMIN',
        'ADMIN',
        'SUPER_ADMIN'
    ),
    ('USER', 'USER', 'SUPER_ADMIN')
ON CONFLICT (name) DO NOTHING;