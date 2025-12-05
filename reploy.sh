#!/bin/bash

echo "Останавливаю и удаляю контейнер..."
docker stop backend 2>/dev/null || echo "Контейнер уже остановлен"
docker rm -f backend 2>/dev/null || echo "Контейнер уже удален"

echo "Удаляю образ..."
docker rmi backend_for_arctic_team-backend 2>/dev/null || echo "Образ уже удален"

echo "Очищаю Maven проект..."
mvn clean

echo "Собираю проект..."
mvn package -DskipTests

echo "Запускаю Docker..."
docker-compose up -d --build

echo "Жду запуска..."
sleep 5

echo "Проверяю логи..."
docker-compose logs --tail=10 backend

echo "Проект пересобран и запущен."
