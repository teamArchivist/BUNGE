
document.addEventListener("DOMContentLoaded", () => {

   let token = $("meta[name='_csrf']").attr("content");
   let header = $("meta[name='_csrf_header']").attr("content");
   // Color variables based on CSS variables.
   const _body = getComputedStyle(document.body);
   const primaryColor = _body.getPropertyValue("--bs-primary");
   const successColor = _body.getPropertyValue("--bs-success");
   const infoColor = _body.getPropertyValue("--bs-info");
   const warningColor = _body.getPropertyValue("--bs-warning");
   const mutedColorRGB = _body.getPropertyValue("--bs-secondary-color-rgb");
   const grayColor = "rgba(180, 180, 180, .5)";

   $.ajax({
      url: "/memo/get-memo-chart-data",
      method: "post",
      contentType: "application/json",
      dataType: "json",
      beforeSend: function (xhr) {
         if (header && token) {
            xhr.setRequestHeader(header, token);
         }
      },
      success: function (response) {
         const barData = response.map(item => ({
            y: item.month,
            a: item.createdMemo,
            b: item.completedBooks
         }));

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
      },
      error: function(xhr, status, error) {
         console.error("데이터를 가져오는 중 오류가 발생했습니다: ", status, error);
      }
   });
});