-- 04-borrow-book-safe-function.sql
-- This file creates a safe borrow function.
-- It prevents:
-- 1. Borrowing without login
-- 2. Borrowing the same book more than once
-- 3. Borrowing a book when available_count is 0
-- 4. Negative available_count values

-- Same user cannot actively borrow the same book more than once.
create unique index if not exists unique_active_loan_per_user_book
on public.loans (user_id, book_id)
where status = 'borrowed';

-- available_count cannot be negative.
do $$
begin
  if not exists (
    select 1
    from pg_constraint
    where conname = 'books_available_count_nonnegative'
  ) then
    alter table public.books
    add constraint books_available_count_nonnegative
    check (available_count >= 0);
  end if;
end $$;

-- Safe borrow function
create or replace function public.borrow_book_safe(p_book_id bigint)
returns text
language plpgsql
security definer
set search_path = public
as $$
declare
  v_user_id uuid := auth.uid();
  v_available_count int;
begin
  if v_user_id is null then
    raise exception 'User is not logged in.';
  end if;

  if exists (
    select 1
    from public.loans
    where user_id = v_user_id
      and book_id = p_book_id
      and status = 'borrowed'
  ) then
    raise exception 'You already borrowed this book.';
  end if;

  select available_count
  into v_available_count
  from public.books
  where id = p_book_id
  for update;

  if v_available_count is null then
    raise exception 'Book not found.';
  end if;

  if v_available_count <= 0 then
    raise exception 'No available copies left.';
  end if;

  update public.books
  set available_count = available_count - 1
  where id = p_book_id;

  insert into public.loans (user_id, book_id, status)
  values (v_user_id, p_book_id, 'borrowed');

  return 'Book borrowed successfully.';
end;
$$;

grant execute on function public.borrow_book_safe(bigint) to authenticated;
