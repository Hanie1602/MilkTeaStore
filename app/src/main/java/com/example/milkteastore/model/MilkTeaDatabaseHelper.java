package com.example.milkteastore.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//LƯU Ý:
//Mỗi lần sửa DB, nhớ cập nhật lại DATABASE_VERSION, tăng lên 1 đơn vị

//Khai báo class kế thừa SQLiteOpenHelper để tạo và quản lý DB
public class MilkTeaDatabaseHelper extends SQLiteOpenHelper {
    //Tên Database
    private static final String DATABASE_NAME = "MilkTeaStore.db";

    //Phiên bản Database
    private static final int DATABASE_VERSION = 2;

    //Contractor, truyền context cho SQLiteOpenHelper
    public MilkTeaDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Tạo DB và các bảng dữ liệu
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Bảng User: thông tin người dùng
        db.execSQL("CREATE TABLE User (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FirstName TEXT, LastName TEXT, Username TEXT, PasswordHash TEXT, Email TEXT, " +
                "PhoneNumber TEXT, Address TEXT, Role TEXT, Image TEXT, " +
                "CreatedTime TEXT, UpdatedTime TEXT, DeletedTime TEXT)");

        //Bảng Category: Phân loại như trà sữa, cà phê, trà,...
        db.execSQL("CREATE TABLE Category (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "CategoryName TEXT, Description TEXT, " +
                "CreatedTime TEXT, UpdatedTime TEXT, DeletedTime TEXT)");

        //Bảng Product: thông tin ly nước
        db.execSQL("CREATE TABLE Product (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "CategoryId INTEGER, Name TEXT, Description TEXT, Price REAL, Size TEXT, Quantity INTEGER, Image TEXT, " +
                "CreatedTime TEXT, UpdatedTime TEXT, DeletedTime TEXT, " +
                "FOREIGN KEY(CategoryId) REFERENCES Category(Id))");

        //Bảng Order: thông tin đơn hàng
        db.execSQL("CREATE TABLE `Order` (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "UserId INTEGER, Code TEXT, OrderDate TEXT, TotalPrice REAL, Status TEXT, CreatedTime TEXT, " +
                "FOREIGN KEY(UserId) REFERENCES User(Id))");

        //Bảng OrderProduct: sản phẩm trong từng đơn hàng
        db.execSQL("CREATE TABLE OrderProduct (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "OrderId INTEGER, ProductId INTEGER, Quantity INTEGER, Price REAL, " +
                "FOREIGN KEY(OrderId) REFERENCES `Order`(Id), " +
                "FOREIGN KEY(ProductId) REFERENCES Product(Id))");

        //Bảng Topping: topping thêm (trân châu, thạch, flan,...)
        db.execSQL("CREATE TABLE Topping (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Name TEXT, Price REAL, Image TEXT, DeletedTime TEXT)");

        //Bảng OrderTopping: topping trong từng ly nước
        db.execSQL("CREATE TABLE OrderTopping (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ToppingId INTEGER, OrderProductId INTEGER, " +
                "FOREIGN KEY(ToppingId) REFERENCES Topping(Id), " +
                "FOREIGN KEY(OrderProductId) REFERENCES OrderProduct(Id))");

        //Bảng Payment: thông tin thanh toán người dùng
        db.execSQL("CREATE TABLE Payment (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "OrderId INTEGER, UserId INTEGER, FullName TEXT, ShippingMethod TEXT, OrderPrice REAL, " +
                "PaymentStatus TEXT, PaymentMethod TEXT, PayTime TEXT, CreateTime TEXT, " +
                "FOREIGN KEY(OrderId) REFERENCES `Order`(Id), " +
                "FOREIGN KEY(UserId) REFERENCES User(Id))");
    }

    //Xử lý khi cập nhật version database: xóa bảng cũ và tạo lại
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Payment");
        db.execSQL("DROP TABLE IF EXISTS OrderTopping");
        db.execSQL("DROP TABLE IF EXISTS Topping");
        db.execSQL("DROP TABLE IF EXISTS OrderProduct");
        db.execSQL("DROP TABLE IF EXISTS `Order`");
        db.execSQL("DROP TABLE IF EXISTS Product");
        db.execSQL("DROP TABLE IF EXISTS Category");
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db); //Gọi làm hàm tạo bảng
    }
}

//Note:
//MilkTeaDatabaseHelper thiết kế toàn bộ database gồm 8 bảng.
//Mỗi bảng thể hiện các Entities trong hệ thống bán trà sữa như:
// người dùng, sản phẩm, topping, đơn hàng và thanh toán.
//Kiến trúc MVC, trong đó MilkTeaDatabaseHelper thuộc Model chịu trách nhiệm thao tác data
//Mỗi bảng đều có khóa chính và khóa ngoại để đảm bảo tính toàn vẹn dữ liệu.