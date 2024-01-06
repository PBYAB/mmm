ALTER TABLE Product
    ADD CONSTRAINT unique_product_barcode UNIQUE (barcode);