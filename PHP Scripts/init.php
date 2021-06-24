<?php
   $host="localhost";
   $user="id16479861_sharelifetoday";
   $pass="xWa06EgV_U2++E<o";
   $db="id16479861_bloodbank";
   
   $con=mysqli_connect($host, $user, $pass, $db);
   if($con)
   {
      // echo "Connected to Database";
       
   }
   else
   {
       //echo "Failed to connect".mysqli_connect_error();
   }
?>