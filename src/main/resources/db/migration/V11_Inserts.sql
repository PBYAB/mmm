insert into allergen (name) values ('Gluten');
insert into allergen (name) values ('Crustaceans');
insert into allergen (name) values ('Eggs');
insert into allergen (name) values ('Fish');
insert into allergen (name) values ('Peanuts');

insert into article_categories (name) values ('Bread');
insert into article_categories (name) values ('Dairy');
insert into article_categories (name) values ('Fruit');
insert into article_categories (name) values ('Vegetables');
insert into article_categories (name) values ('Meat');

insert into articles (category_id, title, content, created_at, status) values (1, 'Lorem ipsum','Lorem Ipsum','2023-01-01', 'DRAFT');
insert into articles (category_id, title, content, created_at, status) values (2, 'Lorem ipsum','Lorem Ipsum','2023-03-01', 'PUBLISHED');
insert into articles (category_id, title, content, created_at, status) values (3, 'Lorem ipsum','Lorem Ipsum','2023-02-01', 'DRAFT');
insert into articles (category_id, title, content, created_at, status) values (4, 'Lorem ipsum','Lorem Ipsum','2023-01-11', 'PUBLISHED');
insert into articles (category_id, title, content, created_at, status) values (5, 'Lorem ipsum','Lorem Ipsum','2023-01-01', 'PUBLISHED');

insert into brand (name) values ('Nestle');
insert into brand (name) values ('Coca Cola');
insert into brand (name) values ('Pepsi');
insert into brand (name) values ('Unilever');
insert into brand (name) values ('Procter & Gamble');

insert into category (name) values ('Beverages');
insert into category (name) values ('Food');
insert into category (name) values ('Cleaning');
insert into category (name) values ('Personal Care');
insert into category (name) values ('Pet Care');

insert into country (name) values ('United States');
insert into country (name) values ('China');
insert into country (name) values ('Japan');
insert into country (name) values ('Germany');
insert into country (name) values ('United Kingdom');

insert into ingredient (name) values ('Water');
insert into ingredient (name) values ('Sugar');
insert into ingredient (name) values ('Salt');
insert into ingredient (name) values ('Pepper');
insert into ingredient (name) values ('Flour');

insert into nutriment (energy_kcal_per_100g, fat_per_100g, fiber_per_100g, proteins_per_100g, salt_per_100g, sugars_per_100g, sodium_per_100g) values (100, 10, 5, 20, 0.5, 10, 0.2);
insert into nutriment (energy_kcal_per_100g, fat_per_100g, fiber_per_100g, proteins_per_100g, salt_per_100g, sugars_per_100g, sodium_per_100g) values (200, 20, 10, 40, 1.0, 20, 0.4);
insert into nutriment (energy_kcal_per_100g, fat_per_100g, fiber_per_100g, proteins_per_100g, salt_per_100g, sugars_per_100g, sodium_per_100g) values (300, 30, 15, 60, 1.5, 30, 0.6);
insert into nutriment (energy_kcal_per_100g, fat_per_100g, fiber_per_100g, proteins_per_100g, salt_per_100g, sugars_per_100g, sodium_per_100g) values (400, 40, 20, 80, 2.0, 40, 0.8);
insert into nutriment (energy_kcal_per_100g, fat_per_100g, fiber_per_100g, proteins_per_100g, salt_per_100g, sugars_per_100g, sodium_per_100g) values (500, 50, 25, 100, 2.5, 50, 1.0);

insert into product_ingredient (name, vegan, vegetarian, from_palm_oil) values ('Water', true, true, false);
insert into product_ingredient (name, vegan, vegetarian, from_palm_oil) values ('Sugar', true, false, false);
insert into product_ingredient (name, vegan, vegetarian, from_palm_oil) values ('Salt', true, true, false);
insert into product_ingredient (name, vegan, vegetarian, from_palm_oil) values ('Pepper', false, true, false);
insert into product_ingredient (name, vegan, vegetarian, from_palm_oil) values ('Flour', true, true, true);

insert into product_ingredient_analysis (vegan, ingredients_description, vegetarian, from_palm_oil) values (true, 'Water, Sugar, Salt, Pepper, Flour', true, false);
insert into product_ingredient_analysis (vegan, ingredients_description, vegetarian, from_palm_oil) values (false, 'Water, Sugar, Salt, Pepper, Flour', true, true);
insert into product_ingredient_analysis (vegan, ingredients_description, vegetarian, from_palm_oil) values (true, 'Water, Sugar, Salt, Pepper, Flour', false, false);
insert into product_ingredient_analysis (vegan, ingredients_description, vegetarian, from_palm_oil) values (false, 'Water, Sugar, Salt, Pepper, Flour', false, true);
insert into product_ingredient_analysis (vegan, ingredients_description, vegetarian, from_palm_oil) values (true, 'Water, Sugar, Salt, Pepper, Flour', true, true);

insert into recipe (name, instructions, servings, energy_kcal_per_serving) values ('Water', 'Boil water', 1, 100);
insert into recipe (name, instructions, servings, energy_kcal_per_serving) values ('Sugar', 'Add sugar to water', 1, 200);
insert into recipe (name, instructions, servings, energy_kcal_per_serving) values ('Salt', 'Add salt to water', 1, 300);
insert into recipe (name, instructions, servings, energy_kcal_per_serving) values ('Pepper', 'Add pepper to water', 1, 400);
insert into recipe (name, instructions, servings, energy_kcal_per_serving) values ('Flour', 'Add flour to water', 1, 500);

insert into recipe_ingredient (recipe_id, ingredient_id, quantity, unit) values (1, 1, 1, 'ML');
insert into recipe_ingredient (recipe_id, ingredient_id, quantity, unit) values (2, 2, 1, 'ML');
insert into recipe_ingredient (recipe_id, ingredient_id, quantity, unit) values (3, 3, 1, 'G');
insert into recipe_ingredient (recipe_id, ingredient_id, quantity, unit) values (4, 4, 1, 'ML');
insert into recipe_ingredient (recipe_id, ingredient_id, quantity, unit) values (5, 5, 1, 'G');

insert into product (name, barcode, quantity, nutri_score, nova_group, ingredient_analysis_id, nutriment_id) values ('Water', '1234567890123', 1, 1, 1, 1, 1);
insert into product (name, barcode, quantity, nutri_score, nova_group, ingredient_analysis_id, nutriment_id) values ('Sugar', '1234567890124', 1, 2, 2, 2, 2);
insert into product (name, barcode, quantity, nutri_score, nova_group, ingredient_analysis_id, nutriment_id) values ('Salt', '1234567890125', 1, 3, 3, 3, 3);
insert into product (name, barcode, quantity, nutri_score, nova_group, ingredient_analysis_id, nutriment_id) values ('Pepper', '1234567890126', 1, 4, 4, 4, 4);
insert into product (name, barcode, quantity, nutri_score, nova_group, ingredient_analysis_id, nutriment_id) values ('Flour', '1234567890127', 1, 5, 5, 5, 5);
