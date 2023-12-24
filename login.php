<?
    $email = $_POST['$email'];
    $password = $_POST['password'];
    

    $con = new mysqli("localhost","root","","dacntt2_webapp");
    if($con->connect_error) {
        die("Failed to connect: ".$con->connect_error);
    } else {
        $stmt = $con->prepare("select * from registration where email = ?");
        $stmt -> bind_param("s",$email);
        $stmt -> execute();
        $stmt_result = $stmt->get_result();
        if($stmt_result->num_rows>0){
            $data = $stmt_result -> fetch_assoc();
            if($data['password'] === $spassword )
                echo "<h2> Login Successfully</h2>";
        } 
        else {
            echo "<h2> Wrong password or account</h2>";
        }
    }
?>