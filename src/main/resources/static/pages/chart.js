document.addEventListener("DOMContentLoaded", () => {
   // Color variables based on CSS variables.
   const _body           = getComputedStyle(document.body);
   const primaryColor    = _body.getPropertyValue("--bs-primary");
   const successColor    = _body.getPropertyValue("--bs-success");
   const infoColor       = _body.getPropertyValue("--bs-info");
   const warningColor    = _body.getPropertyValue("--bs-warning");
   const mutedColorRGB   = _body.getPropertyValue("--bs-secondary-color-rgb");
   const grayColor       = "rgba(180, 180, 180, .5)";

   // Bar Chart Data
   const barData = [
      { y: "2024/1", a: 15, b: 3 },
      { y: "2024/2", a: 10, b: 2 },
      { y: "2024/3", a: 13, b: 4 },
      { y: "2024/4", a: 16, b: 6 },
      { y: "2024/5", a: 20, b: 5 }
   ];

   // Create Bar Chart
   const barChart = new Chart(document.getElementById("_dm-barChart"), {
      type: "bar",
      data: {
         labels: barData.map(item => item.y),
         datasets: [
            {
               label: "생성한 기록",
               data: barData.map(item => item.a),
               borderColor: primaryColor,
               backgroundColor: primaryColor,
            },
            {
               label: "완독한 책",
               data: barData.map(item => item.b),
               borderColor: infoColor,
               backgroundColor: infoColor,
            }
         ]
      },
      options: {
         plugins: {
            legend: {
               display: true,
               align: "end",
               labels: {
                  color: `rgb(${mutedColorRGB})`,
                  boxWidth: 10,
               }
            },
            tooltip: {
               position: "nearest"
            }
         },
         interaction: {
            mode: "index",
            intersect: false,
         },
         scales: {
            y: {
               grid: {
                  borderWidth: 0,
                  color: `rgba(${mutedColorRGB}, .07)`
               },
               suggestedMax: 30,
               ticks: {
                  font: { size: 11 },
                  color: `rgb(${mutedColorRGB})`,
                  beginAtZero: false,
                  stepSize: 5
               }
            },
            x: {
               grid: {
                  borderWidth: 0,
                  drawOnChartArea: false
               },
               ticks: {
                  font: { size: 11 },
                  color: `rgb(${mutedColorRGB})`,
                  autoSkip: true,
                  maxRotation: 0,
                  minRotation: 0,
                  maxTicksLimit: 7
               }
            }
         },
         elements: {
            point: {
               radius: 3,
               hoverRadius: 5
            },
            line: {
               tension: 0.2
            }
         }
      }
   });
});