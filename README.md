# Satellite Tracking System

A full-stack web application for real-time satellite tracking and visualization using Two-Line Element (TLE) data.

## Overview

This project provides an interactive platform to track and visualize satellites in orbit around Earth. The system fetches TLE (Two-Line Element) data, processes orbital information, and renders satellite positions and trajectories in an immersive 3D globe interface.

## Technology Stack

### Backend (Java)
- Retrieves and processes TLE data from public satellite databases
- Parses and validates Two-Line Element sets
- Calculates satellite orbital positions and trajectories
- Provides RESTful API endpoints for satellite data
- Handles real-time data updates and caching

### Frontend (React + Cesium)
- Built with React for component-based UI architecture
- Integrates CesiumJS library for 3D geospatial visualization
- Renders Earth globe with satellite positions in real-time
- Displays satellite orbital paths and coverage areas
- Interactive controls for satellite selection and tracking
- Time-based simulation capabilities

## Key Features

- Real-time satellite position tracking
- 3D visualization of orbital trajectories
- Search and filter satellites by name or category
- Historical and predictive orbit rendering
- Interactive globe navigation and camera controls
- Satellite information panels with orbital parameters

## Getting Started

### Prerequisites
- Java 11+
- Node.js 16+
- npm or yarn

### Installation

1. Clone the repository
```bash
git clone <repository-url>
cd satellite-tracking-system
```

2. Start the backend
```bash
cd backend
./mvnw spring-boot:run
```

3. Start the frontend
```bash
cd frontend
npm install
npm start
```
