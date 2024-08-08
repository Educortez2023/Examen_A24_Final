<?php
require '../vendor/autoload.php';

use Slim\Factory\AppFactory;

$app = AppFactory::create();

// Agregar middleware para manejo de JSON
$app->addBodyParsingMiddleware();

// Configuración de la conexión a la base de datos
$db = new PDO('mysql:host=localhost;dbname=bienesraices', 'root', '');

// Middleware para establecer la conexión a la base de datos
$app->add(function ($request, $handler) use ($db) {
    $request = $request->withAttribute('db', $db);
    return $handler->handle($request);
});

// Endpoints para Propiedades
$app->post('/api/crearPropiedad', function ($request, $response) {
    $db = $request->getAttribute('db');
    $data = $request->getParsedBody();
    
    $stmt = $db->prepare("INSERT INTO propiedades (direccion, precio, descripcion, id_agente) VALUES (?, ?, ?, ?)");
    $result = $stmt->execute([$data['direccion'], $data['precio'], $data['descripcion'], $data['id_agente']]);
    
    if ($result) {
        $response->getBody()->write(json_encode(['status' => 'Propiedad registrada exitosamente']));
    } else {
        $response->getBody()->write(json_encode(['status' => 'error', 'message' => 'No se pudo guardar la propiedad']));
    }
    return $response->withHeader('Content-Type', 'application/json');
});

$app->post('/api/actualizarPropiedad', function ($request, $response) {
    $db = $request->getAttribute('db');
    $data = $request->getParsedBody();

    $stmt = $db->prepare("UPDATE propiedades SET direccion = ?, precio = ?, descripcion = ?, id_agente = ? WHERE id = ?");
    $result = $stmt->execute([$data['direccion'], $data['precio'], $data['descripcion'], $data['id_agente'], $data['id']]);

    if ($result) {
        $response->getBody()->write(json_encode(['status' => 'Propiedad actualizada exitosamente']));
    } else {
        $response->getBody()->write(json_encode(['status' => 'error', 'message' => 'No se pudo actualizar la propiedad']));
    }
    return $response->withHeader('Content-Type', 'application/json');
});

$app->post('/api/eliminarPropiedad', function ($request, $response) {
    $db = $request->getAttribute('db');
    $data = $request->getParsedBody();

    $stmt = $db->prepare("DELETE FROM propiedades WHERE id = ?");
    $result = $stmt->execute([$data['id']]);

    if ($result) {
        $response->getBody()->write(json_encode(['status' => 'Propiedad eliminada exitosamente']));
    } else {
        $response->getBody()->write(json_encode(['status' => 'error', 'message' => 'No se pudo eliminar la propiedad']));
    }
    return $response->withHeader('Content-Type', 'application/json');
});

$app->post('/api/listarPropiedades', function ($request, $response) {
    $db = $request->getAttribute('db');

    $stmt = $db->query("SELECT * FROM propiedades");
    $properties = $stmt->fetchAll(PDO::FETCH_ASSOC);

    $response->getBody()->write(json_encode($properties));
    return $response->withHeader('Content-Type', 'application/json');
});

$app->post('/api/detallesPropiedad', function ($request, $response) {
    $db = $request->getAttribute('db');
    $data = $request->getParsedBody();

    $stmt = $db->prepare("SELECT * FROM propiedades WHERE id = ?");
    $stmt->execute([$data['id']]);
    $property = $stmt->fetch(PDO::FETCH_ASSOC);

    if ($property) {
        $response->getBody()->write(json_encode($property));
    } else {
        $response->getBody()->write(json_encode(['status' => 'error', 'message' => 'Propiedad no encontrada']));
    }
    return $response->withHeader('Content-Type', 'application/json');
});

// Endpoints para Agentes
$app->post('/api/crearAgente', function ($request, $response) {
    $db = $request->getAttribute('db');
    $data = $request->getParsedBody();

    $stmt = $db->prepare("INSERT INTO agentes (nombre, telefono, email) VALUES (?, ?, ?)");
    $result = $stmt->execute([$data['nombre'], $data['telefono'], $data['email']]);

    if ($result) {
        $response->getBody()->write(json_encode(['status' => 'Agente registrado exitosamente']));
    } else {
        $response->getBody()->write(json_encode(['status' => 'error', 'message' => 'No se pudo guardar el agente']));
    }
    return $response->withHeader('Content-Type', 'application/json');
});

$app->post('/api/actualizarAgente', function ($request, $response) {
    $db = $request->getAttribute('db');
    $data = $request->getParsedBody();

    $stmt = $db->prepare("UPDATE agentes SET nombre = ?, telefono = ?, email = ? WHERE id = ?");
    $result = $stmt->execute([$data['nombre'], $data['telefono'], $data['email'], $data['id']]);

    if ($result) {
        $response->getBody()->write(json_encode(['status' => 'Agente actualizado exitosamente']));
    } else {
        $response->getBody()->write(json_encode(['status' => 'error', 'message' => 'No se pudo actualizar el agente']));
    }
    return $response->withHeader('Content-Type', 'application/json');
});

$app->post('/api/eliminarAgente', function ($request, $response) {
    $db = $request->getAttribute('db');
    $data = $request->getParsedBody();

    $stmt = $db->prepare("DELETE FROM agentes WHERE id = ?");
    $result = $stmt->execute([$data['id']]);

    if ($result) {
        $response->getBody()->write(json_encode(['status' => 'Agente eliminado exitosamente']));
    } else {
        $response->getBody()->write(json_encode(['status' => 'error', 'message' => 'No se pudo eliminar el agente']));
    }
    return $response->withHeader('Content-Type', 'application/json');
});

$app->post('/api/listarAgentes', function ($request, $response) {
    $db = $request->getAttribute('db');

    $stmt = $db->query("SELECT * FROM agentes");
    $agents = $stmt->fetchAll(PDO::FETCH_ASSOC);

    $response->getBody()->write(json_encode($agents));
    return $response->withHeader('Content-Type', 'application/json');
});

$app->post('/api/detallesAgente', function ($request, $response) {
    $db = $request->getAttribute('db');
    $data = $request->getParsedBody();

    $stmt = $db->prepare("SELECT * FROM agentes WHERE id = ?");
    $stmt->execute([$data['id']]);
    $agent = $stmt->fetch(PDO::FETCH_ASSOC);

    if ($agent) {
        $response->getBody()->write(json_encode($agent));
    } else {
        $response->getBody()->write(json_encode(['status' => 'error', 'message' => 'Agente no encontrado']));
    }
    return $response->withHeader('Content-Type', 'application/json');
});

// Endpoints para Clientes
$app->post('/api/crearCliente', function ($request, $response) {
    $db = $request->getAttribute('db');
    $data = $request->getParsedBody();

    $stmt = $db->prepare("INSERT INTO clientes (nombre, telefono, email) VALUES (?, ?, ?)");
    $result = $stmt->execute([$data['nombre'], $data['telefono'], $data['email']]);

    if ($result) {
        $response->getBody()->write(json_encode(['status' => 'Cliente registrado exitosamente']));
    } else {
        $response->getBody()->write(json_encode(['status' => 'error', 'message' => 'No se pudo guardar el cliente']));
    }
    return $response->withHeader('Content-Type', 'application/json');
});

$app->post('/api/actualizarCliente', function ($request, $response) {
    $db = $request->getAttribute('db');
    $data = $request->getParsedBody();

    $stmt = $db->prepare("UPDATE clientes SET nombre = ?, telefono = ?, email = ? WHERE id = ?");
    $result = $stmt->execute([$data['nombre'], $data['telefono'], $data['email'], $data['id']]);

    if ($result) {
        $response->getBody()->write(json_encode(['status' => 'Cliente actualizado exitosamente']));
    } else {
        $response->getBody()->write(json_encode(['status' => 'error', 'message' => 'No se pudo actualizar el cliente']));
    }
    return $response->withHeader('Content-Type', 'application/json');
});

$app->post('/api/eliminarCliente', function ($request, $response) {
    $db = $request->getAttribute('db');
    $data = $request->getParsedBody();

    $stmt = $db->prepare("DELETE FROM clientes WHERE id = ?");
    $result = $stmt->execute([$data['id']]);

    if ($result) {
        $response->getBody()->write(json_encode(['status' => 'Cliente eliminado exitosamente']));
    } else {
        $response->getBody()->write(json_encode(['status' => 'error', 'message' => 'No se pudo eliminar el cliente']));
    }
    return $response->withHeader('Content-Type', 'application/json');
});

$app->post('/api/listarClientes', function ($request, $response) {
    $db = $request->getAttribute('db');

    $stmt = $db->query("SELECT * FROM clientes");
    $clients = $stmt->fetchAll(PDO::FETCH_ASSOC);

    $response->getBody()->write(json_encode($clients));
    return $response->withHeader('Content-Type', 'application/json');
});

$app->post('/api/detallesCliente', function ($request, $response) {
    $db = $request->getAttribute('db');
    $data = $request->getParsedBody();

    $stmt = $db->prepare("SELECT * FROM clientes WHERE id = ?");
    $stmt->execute([$data['id']]);
    $client = $stmt->fetch(PDO::FETCH_ASSOC);

    if ($client) {
        $response->getBody()->write(json_encode($client));
    } else {
        $response->getBody()->write(json_encode(['status' => 'error', 'message' => 'Cliente no encontrado']));
    }
    return $response->withHeader('Content-Type', 'application/json');
});

$app->run(); 