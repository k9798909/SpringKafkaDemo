CREATE TABLE sent_email (
    id SERIAL PRIMARY KEY,  
    name VARCHAR(100) NOT NULL,  
    email VARCHAR(100) NOT NULL,  
    sent_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP 
);

COMMENT ON TABLE sent_email IS '已寄發郵件的表格';
COMMENT ON COLUMN sent_email.id IS '自增主鍵';
COMMENT ON COLUMN sent_email.name IS '收件人的姓名';
COMMENT ON COLUMN sent_email.email IS '收件人的電子郵件';
COMMENT ON COLUMN sent_email.sent_time IS '郵件的寄發時間';