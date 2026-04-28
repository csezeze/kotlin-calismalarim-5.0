-- 01-create-tables.sql
-- This file creates the main database tables for LibraryApp.

-- PROFILES TABLE
-- Stores extra user information.
create table if not exists public.profiles (
  id uuid primary key references auth.users(id) on delete cascade,
  full_name text,
  email text,
  created_at timestamp with time zone default now()
);

-- BOOKS TABLE
-- Stores books in the library.
create table if not exists public.books (
  id bigint generated always as identity primary key,
  title text not null,
  author text not null,
  category text,
  description text,
  image_url text,
  available_count int default 1,
  created_at timestamp with time zone default now()
);

-- LOANS TABLE
-- Stores borrowed book records.
create table if not exists public.loans (
  id bigint generated always as identity primary key,
  user_id uuid references auth.users(id) on delete cascade,
  book_id bigint references public.books(id) on delete cascade,
  borrowed_at timestamp with time zone default now(),
  due_at timestamp with time zone,
  returned_at timestamp with time zone,
  status text default 'borrowed'
);
