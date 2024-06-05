CREATE TABLE trades (
                        tradeNo INT AUTO_INCREMENT PRIMARY KEY,
                        sellerID VARCHAR(255) NOT NULL,
                        title VARCHAR(255) NOT NULL,
                        description TEXT NOT NULL,
                        price INT NOT NULL,
                        categoryID VARCHAR(255) NOT NULL,
                        selling VARCHAR(255) NOT NULL,
                        status VARCHAR(255) NOT NULL,
                        conditions VARCHAR(255) NOT NULL,
                        tradeMethod VARCHAR(255) NOT NULL,
                        locations VARCHAR(255) NOT NULL,
                        readCount INT DEFAULT 0,
                        likes INT DEFAULT 0
);

CREATE TABLE trade_images (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              tradeNo INT NOT NULL,
                              image_url VARCHAR(255) NOT NULL,
                              FOREIGN KEY (tradeNo) REFERENCES trades(tradeNo) ON DELETE CASCADE
);
