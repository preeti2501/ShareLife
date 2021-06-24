<?php
    require 'init.php';
    $blood_group=$_POST["blood_group"];
    $name=$_POST["name"];
    $state=$_POST["state"];
    $city=$_POST["city"];
    $number=$_POST["number"];
    $password=$_POST["password"];
    
    $sql="INSERT INTO user_table (blood_group, name, state, city, number, password) VALUES('$blood_group', '$name', '$state', '$city', '$number', '$password')";
    $result=mysqli_query($con, $sql);
    if($result){
        echo " Great Success!";
    }
    else
    {
        echo "Error: ".mysqli_error($con);
    }
    mysqli_close($con);

?>