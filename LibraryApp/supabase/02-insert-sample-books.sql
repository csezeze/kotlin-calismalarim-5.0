-- 02-insert-sample-books.sql
-- This file inserts sample books into the books table.
-- The WHERE NOT EXISTS parts prevent duplicate book records.

insert into public.books
(title, author, category, description, image_url, available_count)
select
  'The Little Prince',
  'Antoine de Saint-Exupery',
  'Classic',
  'A short and emotional story about friendship, loneliness, and seeing the world with the heart.',
  '',
  3
where not exists (
  select 1 from public.books where title = 'The Little Prince'
);

insert into public.books
(title, author, category, description, image_url, available_count)
select
  'Atomic Habits',
  'James Clear',
  'Self Development',
  'A practical book about building good habits and breaking bad ones step by step.',
  '',
  2
where not exists (
  select 1 from public.books where title = 'Atomic Habits'
);

insert into public.books
(title, author, category, description, image_url, available_count)
select
  'Clean Code',
  'Robert C. Martin',
  'Programming',
  'A popular programming book about writing clean, readable, and maintainable code.',
  '',
  1
where not exists (
  select 1 from public.books where title = 'Clean Code'
);

insert into public.books
(title, author, category, description, image_url, available_count)
select
  '1984',
  'George Orwell',
  'Dystopian',
  'A novel about surveillance, control, freedom, and the power of information.',
  '',
  4
where not exists (
  select 1 from public.books where title = '1984'
);

insert into public.books
(title, author, category, description, image_url, available_count)
select
  'Introduction to Algorithms',
  'Cormen, Leiserson, Rivest, Stein',
  'Computer Science',
  'A famous textbook about algorithms, data structures, and problem solving.',
  '',
  2
where not exists (
  select 1 from public.books where title = 'Introduction to Algorithms'
);

insert into public.books
(title, author, category, description, image_url, available_count)
select
  'Kürk Mantolu Madonna',
  'Sabahattin Ali',
  'Novel',
  'A Turkish novel about love, loneliness, and inner feelings.',
  '',
  2
where not exists (
  select 1 from public.books where title = 'Kürk Mantolu Madonna'
);
