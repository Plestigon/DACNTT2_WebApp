import React, { useEffect } from "react";

function Forbidden() {
  useEffect(() => {
    document.title = 'Forbidden - TDTU EMS';
  }, []);

  return (
    <div class="w-100 text-center h2 mt-5">
      403 FORBIDDEN
    </div>
  );
}

export default Forbidden;