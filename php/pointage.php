<?php
$matricule = $_POST['matricule'];
$apiBaseUrl = "http://localhost:8080/api"; 


$data = [
    'matricule' => $matricule,
    'dateDuJour' => date('Y-m-d'),
    'heureArrive' => date('H:i:s'),
];


$curl = curl_init();


curl_setopt_array($curl, [
    CURLOPT_URL => "$apiBaseUrl/pointages",
    CURLOPT_RETURNTRANSFER => true,
    CURLOPT_POST => true,
    CURLOPT_POSTFIELDS => json_encode($data),
    CURLOPT_HTTPHEADER => [
        'Accept: application/json',
        'Content-Type: application/json',
    ],
]);


$response = curl_exec($curl);


if (curl_errno($curl)) {
    die("Erreur cURL : " . curl_error($curl));
}


curl_close($curl);


$apiResponse = json_decode($response, true);


if (!$apiResponse || isset($apiResponse['error'])) {
    die("Erreur : Impossible d'enregistrer le pointage.");
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pointage</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f9;
        }
        .container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background: white;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            text-align: center;
        }
        h2 {
            color: #28a745;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Pointage enregistré avec succès pour <?= htmlspecialchars($matricule) ?>.</h2>
        <a href="employee.php">
            <button>Retour</button>
        </a>
    </div>
</body>
</html>
