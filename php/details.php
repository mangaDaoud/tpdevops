<?php
$matricule = $_GET['matricule'];
$apiBaseUrl = "http://localhost:8084/api";


$curl = curl_init();


curl_setopt_array($curl, [
    CURLOPT_URL => "$apiBaseUrl/employees/$matricule", 
    CURLOPT_RETURNTRANSFER => true,
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
    die("Erreur : Impossible de récupérer les informations de l'employé.");
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Employé - Détails</title>
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
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th {
            background-color: #f4f4f9;
            font-weight: bold;
        }
        button {
            background-color: #28a745;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Détails de l'Employé</h2>
        <table>
            <tr>
                <th>Matricule</th>
                <td><?= htmlspecialchars($apiResponse['matricule']) ?></td>
            </tr>
            <tr>
                <th>Prénom</th>
                <td><?= htmlspecialchars($apiResponse['prenom']) ?></td>
            </tr>
            <tr>
                <th>Nom</th>
                <td><?= htmlspecialchars($apiResponse['nom']) ?></td>
            </tr>
            <tr>
                <th>Email</th>
                <td><?= htmlspecialchars($apiResponse['email']) ?></td>
            </tr>
            <tr>
                <th>Fonction</th>
                <td><?= htmlspecialchars($apiResponse['fonction']) ?></td>
            </tr>
            <tr>
                <th>Structure</th>
                <td><?= htmlspecialchars($apiResponse['structure']) ?></td>
            </tr>
        </table>
        <form action="pointage.php" method="POST">
            <input type="hidden" name="matricule" value="<?= htmlspecialchars($apiResponse['matricule']) ?>">
            <button type="submit">Pointer</button>
        </form>
    </div>
</body>
</html>
