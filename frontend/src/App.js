import React, { useEffect, useState, useRef } from "react";
import { Viewer, Entity } from "resium";
import { Cartesian3, Color } from "cesium";
import * as satellite from "satellite.js";

window.CESIUM_BASE_URL = '/cesium';

function App() {
  const [satellites, setSatellites] = useState([]);
  const viewerRef = useRef(null);

  useEffect(() => {
    fetch("http://localhost:8080/api/satellites")
      .then(res => res.json())
      .then(data => {
        const satsWithPos = data
          .filter(sat => sat.TLE_LINE1 && sat.TLE_LINE2)
          .map(sat => {
            const satrec = satellite.twoline2satrec(sat.TLE_LINE1, sat.TLE_LINE2);
            const now = new Date();
            const posVel = satellite.propagate(satrec, now);
            if (!posVel.position) return null;

            const gmst = satellite.gstime(now);
            const gdPos = satellite.eciToGeodetic(posVel.position, gmst);

            const lat = satellite.radiansToDegrees(gdPos.latitude);
            const lng = satellite.radiansToDegrees(gdPos.longitude);
            const alt = gdPos.height * 1000; // m

            console.log(sat.OBJECT_NAME, { lat, lng, alt }); // debug

            return { name: sat.OBJECT_NAME, lat, lng, alt };
          })
          .filter(Boolean);

        console.log("Positions calculées :", satsWithPos);

        setSatellites(satsWithPos);

        // centrer caméra sur le premier satellite
        if (satsWithPos.length > 0) {
          viewerRef.current?.camera.flyTo({
            destination: Cartesian3.fromDegrees(
              satsWithPos[0].lng,
              satsWithPos[0].lat,
              satsWithPos[0].alt + 500_000
            ),
          });
        }
      })
      .catch(err => console.error(err));
  }, []);

  return (
    <Viewer full ref={viewerRef}>
      {satellites.map((sat, i) => (
        <Entity
          key={i}
          name={sat.name}
          position={Cartesian3.fromDegrees(sat.lng, sat.lat, sat.alt)}
          point={{ pixelSize: 8, color: Color.RED }}
        />
      ))}
    </Viewer>
  );
}

export default App;
