INSERT INTO public.users(
    username,
    password,
    authorities,
    created_at,
    enabled,
    expired,
    locked,
    credentials_expired
)
VALUES (
    'freeplan@example.com',
    '$2a$10$fsD9VR/Zn0voWu.zgfRaLOopJaYXkwhaVm90SfxfGuaGsVRR7LAbq',
    array[''],
    '2023-01-01 11:59:59.000',
    true,
    false,
    false,
    false
);

INSERT INTO public.account(
    first_name,
    last_name,
    gender,
    plan,
    user_id,
    verified
)
VALUES (
    'Free',
    'Plan',
    'M',
    1,
    1,
    true
);

INSERT INTO public.users(
    username,
    password,
    authorities,
    created_at,
    enabled,
    expired,
    locked,
    credentials_expired
)
VALUES (
    'premiumplan@example.com',
    '$2a$10$fsD9VR/Zn0voWu.zgfRaLOopJaYXkwhaVm90SfxfGuaGsVRR7LAbq',
    array[''],
    '2023-01-01 11:59:59.000',
    true,
    false,
    false,
    false
);

INSERT INTO public.account(
    first_name,
    last_name,
    gender,
    plan,
    user_id,
    verified
)
VALUES (
    'Premium',
    'Plan',
    'M',
    2,
    2,
    true
);

INSERT INTO public.post(title, body, author_id) VALUES('A post', '...and its body.', 1);

INSERT INTO public.comment(body, post_id, author_id) VALUES('Free Comment 0', 1, 1);
INSERT INTO public.comment(body, post_id, author_id) VALUES('Free Comment 1', 1, 1);
INSERT INTO public.comment(body, post_id, author_id) VALUES('Free Comment 2', 1, 1);
INSERT INTO public.comment(body, post_id, author_id) VALUES('Free Comment 3', 1, 1);
INSERT INTO public.comment(body, post_id, author_id) VALUES('Free Comment 4', 1, 1);

INSERT INTO public.comment(body, post_id, author_id) VALUES('Premium Comment 0', 1, 2);
INSERT INTO public.comment(body, post_id, author_id) VALUES('Premium Comment 1', 1, 2);
INSERT INTO public.comment(body, post_id, author_id) VALUES('Premium Comment 2', 1, 2);
INSERT INTO public.comment(body, post_id, author_id) VALUES('Premium Comment 3', 1, 2);
INSERT INTO public.comment(body, post_id, author_id) VALUES('Premium Comment 4', 1, 2);