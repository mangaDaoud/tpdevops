<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Employé - Informations</title>
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
        .form-group {
            margin-bottom: 15px;
        }
        label {
            font-weight: bold;
        }
        input {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        button {
            background-color: #007BFF;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Rechercher un Employé</h2>
        <form action="details.php" method="GET">
            <div class="form-group">
                <label for="matricule">Matricule :</label>
                <input type="text" id="matricule" name="matricule" required>
            </div>
            <button type="submit">Rechercher</button>
        </form>
    </div>
</body>
</html>
