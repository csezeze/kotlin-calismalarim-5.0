-- 03-rls-policies.sql
-- RLS means Row Level Security.
-- These rules control who can read or insert data.

-- Enable RLS
alter table public.profiles enable row level security;
alter table public.books enable row level security;
alter table public.loans enable row level security;

-- Drop old policies if they already exist
drop policy if exists "Anyone can view books" on public.books;

drop policy if exists "Users can view own profile" on public.profiles;
drop policy if exists "Users can insert own profile" on public.profiles;
drop policy if exists "Users can update own profile" on public.profiles;

drop policy if exists "Users can view own loans" on public.loans;
drop policy if exists "Users can create own loans" on public.loans;

-- BOOKS TABLE
-- Everyone can view books.
create policy "Anyone can view books"
on public.books
for select
using (true);

-- PROFILES TABLE
-- Users can view only their own profile.
create policy "Users can view own profile"
on public.profiles
for select
using (auth.uid() = id);

-- Users can insert only their own profile.
create policy "Users can insert own profile"
on public.profiles
for insert
with check (auth.uid() = id);

-- Users can update only their own profile.
create policy "Users can update own profile"
on public.profiles
for update
using (auth.uid() = id)
with check (auth.uid() = id);

-- LOANS TABLE
-- Users can view only their own borrowed book records.
create policy "Users can view own loans"
on public.loans
for select
using (auth.uid() = user_id);

-- Users can create only their own loan records.
create policy "Users can create own loans"
on public.loans
for insert
with check (auth.uid() = user_id);
