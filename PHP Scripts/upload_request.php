<?php
require "init.php";
        $m=$_POST["message"];
        $number=$_POST["number"];
        $sql="INSERT INTO `posts` (`id`, `message`, `number`) VALUES (NULL, '$m', '$number');";
        $result= mysqli_query( $con, $sql);
      
     if($result){
         echo "Uploaded!!!";
         
    }
    else
    {
        echo "NOT Uploaded!!! _ Error While Uploading";
    }
mysqli_close($con);

?>