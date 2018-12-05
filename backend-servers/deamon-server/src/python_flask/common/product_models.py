# -*- coding:utf8 -*-


from flask import Flask
from flask_sqlalchemy import SQLAlchemy

from python_flask import app

db = SQLAlchemy(app)
db.init_app(app)

class Product(db.Model):
    __tablename__ = 'product'
    id = db.Column(db.Integer, nullable=False, primary_key=True, autoincrement=True)
    spu = db.Column(db.String(64))
    title = db.Column(db.String(255))
    category_id = db.Column(db.Integer)
    classify_enum = db.Column(db.String(32))
    inner_name = db.Column(db.String(128))
    main_image_url = db.Column(db.String(255))
    source_enum = db.Column(db.String(255))
    source_url = db.Column(db.String(255))
    purchase_url = db.Column(db.String(255))
    state = db.Column(db.String(64))
    state_time = db.Column(db.DateTime)
    create_at = db.Column(db.DateTime)
    update_at = db.Column(db.DateTime)
    total_stock = db.Column(db.Integer)
    memo = db.Column(db.String(255))
    creator_id = db.Column(db.Integer)
    creator = db.Column(db.String(32))
    checker_id = db.Column(db.Integer)
    checker = db.Column(db.String(32))
    product_new_id = db.Column(db.Integer)

    def __repr__(self):
        return '<Product id:%r, name:%r>' %(self.id, self.name)

class Category(db.Model):
    __tablename__ = 'category'

    id = db.Column(db.Integer, nullable=False, primary_key=True, autoincrement=True)
    create_at = db.Column(db.DateTime)
    update_at = db.Column(db.DateTime)
    name = db.Column(db.String(255))
    parent_id = db.Column(db.Integer)
    no = db.Column(db.String(32))
    sort_no = db.Column(db.Integer)
    usable = db.Column(db.Integer)
    creator_id = db.Column(db.Integer)
    creator = db.Column(db.String(32))
    leaf = db.Column(db.Integer)

    def __repr__(self):
        return '<Category id:%r, no: %r, name:%r>' %(self.id,self.no,self.name)