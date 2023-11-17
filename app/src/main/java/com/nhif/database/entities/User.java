package com.nhif.database.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nhif.utils.RunnableWithParam;


import java.io.ByteArrayOutputStream;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private Long num;

    private String firstName;
    private String lastName;
    private String id;
    private String email;
    private String phone;

    private Long dateOfBirth;

    private String gender;

    private String password;

    private String profile_picture;

    public Long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    public Bitmap getProfile_picture() {
//        return profile_picture;
//    }

    public void setProfile_picture(Bitmap profile_picture,Runnable success,Runnable error) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images");

        // Convert Bitmap to a byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        profile_picture.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageData = baos.toByteArray();


        UploadTask uploadTask = storageRef.child("profile"+getEmail()+".jpg").putBytes(imageData);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            if(success!=null)
                success.run();

        }).addOnFailureListener(e -> {
            if(error!=null)
                error.run();

        });

    }


    public void retrieveProfilePicture(RunnableWithParam<Bitmap> success, Runnable error) {
        // Assuming 'storageRef' is a reference to your Firebase Storage location
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images");

        storageRef.child("profile" + getEmail() + ".jpg").getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            if (success != null) {
                success.run(bitmap);
            }
        }).addOnFailureListener(e -> {
            if (error != null) {
                error.run();
            }
        });
    }


}
