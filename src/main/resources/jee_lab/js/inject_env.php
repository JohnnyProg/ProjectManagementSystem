<?php
header('Content-Type: application/javascript');

$appApiUrl = getenv('APP_API_URL') ?: '';
echo "window.APP_API_URL = '" . htmlspecialchars($appApiUrl, ENT_QUOTES, 'UTF-8') . "';";
?>