INSERT INTO device_inventory (
    id,
    kickston_id,
    device_username,
    device_password,
    manufacture_date_time,
    manufacture_factory_place,
    created_date,
    deleted
) VALUES
(gen_random_uuid(),'000001', 'deviceUser01', 'pswrd01', '2025-01-25 10:15:00', 'China_Hub_01', CURRENT_TIMESTAMP, false),
(gen_random_uuid(),'0000A2', 'deviceUser02', 'pswrd02', '2025-01-26 11:20:00', 'China_Hub_02', CURRENT_TIMESTAMP, false),
(gen_random_uuid(),'00FF10', 'deviceUser03', 'pswrd03', '2025-01-28 12:30:00', 'China_Hub_03', CURRENT_TIMESTAMP, false),
(gen_random_uuid(),'01ABCD', 'deviceUser04', 'pswrd04', '2025-01-26 13:45:00', 'China_Hub_04', CURRENT_TIMESTAMP, false),
(gen_random_uuid(),'0F1234', 'deviceUser05', 'pswrd05', '2025-01-24 14:10:00', 'China_Hub_05', CURRENT_TIMESTAMP, false),
(gen_random_uuid(),'10A0B1', 'deviceUser06', 'pswrd06', '2025-01-25 15:05:00', 'China_Hub_06', CURRENT_TIMESTAMP, false),
(gen_random_uuid(),'ABCDEF', 'deviceUser07', 'pswrd07', '2025-01-26 16:40:00', 'China_Hub_07', CURRENT_TIMESTAMP, false),
(gen_random_uuid(),'FFFFFF', 'deviceUser08', 'pswrd08', '2025-01-27 17:55:00', 'China_Hub_08', CURRENT_TIMESTAMP, false)
ON CONFLICT (kickston_id) DO NOTHING;