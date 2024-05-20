// Color variables based on css variable.
// ----------------------------------------------
const _body           = getComputedStyle( document.body );
let primaryColor, headingsColor, mutedColorRGB, dangerColor, infoColor, warningColor, successColor, lineChart, gridColor, gridMainColor;



// Get all variable colors
// ----------------------------------------------
let updateColorVars = () => {

   // Update all variable colors
   primaryColor    = window.getComputedStyle(document.querySelector(".page-title")).color; // "red" //`rgba( ${_body.getPropertyValue( "--bs-primary-color-rgb" )}, .75)`;
   headingsColor    = window.getComputedStyle(document.querySelector(".text-body-emphasis")).color; //_body.getPropertyValue( "--bs-primary-color" );
   mutedColorRGB   = `rgba( ${_body.getPropertyValue( "--bs-secondary-color-rgb" )}, .5)`;
   dangerColor     = _body.getPropertyValue("--bs-danger");
   infoColor       = _body.getPropertyValue("--bs-info");
   warningColor    = _body.getPropertyValue("--bs-warning");
   successColor    = _body.getPropertyValue("--bs-success");
   gridColor       = mutedColorRGB.replace(/^(.*,)(.*)\)/g, "$1 .075)");
   gridMainColor   = mutedColorRGB.replace(/^(.*,)(.*)\)/g, "$1 .575)");
   return;
}

const getGridYColor = function( context ) {
   if (context.index > 0) {
      return gridColor;
   } else if (context.index == 0) {
      return gridMainColor;
   }
}

const getGridXColor = function( context ) {
   if (context.index > 0) {
      return "transparent";
   } else if (context.index == 0) {
      return gridMainColor;
   }
}

document.addEventListener( "DOMContentLoaded", () => {

   // Update color variables based on css variable.
   // ----------------------------------------------
   updateColorVars();



   // Line Chart
   // ----------------------------------------------
   const lineData = [
      {'elapsed': 'Jan 1', 'value': 18}, {'elapsed': 'Jan 2', 'value': 24}, {'elapsed': 'Jan 3', 'value': 9}, {'elapsed': 'Jan 4', 'value': 12}, {'elapsed': 'Jan 5', 'value': 13}, {'elapsed': 'Jan 6', 'value': 22}, {'elapsed': 'Jan 7', 'value': 11}, {'elapsed': 'Jan 8', 'value': 26}, {'elapsed': 'Jan 9', 'value': 12}, {'elapsed': 'Jan 10', 'value': 19},
      {'elapsed': 'Jan 11', 'value': 18}, {'elapsed': 'Jan 12', 'value': 24}, {'elapsed': 'Jan 13', 'value': 9}, {'elapsed': 'Jan 14', 'value': 12}, {'elapsed': 'Jan 15', 'value': 13}, {'elapsed': 'Jan 16', 'value': 22}, {'elapsed': 'Jan 17', 'value': 11}, {'elapsed': 'Jan 18', 'value': 26}, {'elapsed': 'Jan 19', 'value': 12}, {'elapsed': 'Jan 20', 'value': 19}
   ];

   lineChart = new Chart( document.getElementById('_dm-lineChart'), {
      type: 'line',
      data: {
         datasets: [
            {
               label: 'Recent Sales',
               data: lineData,
               borderWidth: 1.75,
               borderColor: primaryColor,
               backgroundColor: primaryColor,
               parsing: {
                  xAxisKey: 'elapsed',
                  yAxisKey: 'value'
               }
            }
         ]
      },
      options: {
         plugins: {
            title: {
               display: true,
               color: headingsColor,
               text: 'Recent Sales Chart'
            },
            legend: {
               display: true,
               labels: {
                  color: headingsColor,
                  boxWidth: 10,
               }
            }
         },
         // Tooltip mode
         interaction: {
            intersect: false,
         },

         responsive: true,
         maintainAspectRatio: false,

         scales: {
            y: {
               grid: {
                  color: getGridYColor,
                  lineWidth: 2
               },
               suggestedMax: 30,
               ticks: {
                  font: { size: 11 },
                  color: headingsColor,
                  beginAtZero: false,
                  stepSize: 5
               }
            },
            x: {
               grid: {
                  color: getGridXColor,
                  lineWidth: 2
               },
               ticks: {
                  font: { size: 12 },
                  color: headingsColor,
                  autoSkip: true,
                  maxRotation: 0,
                  minRotation: 0,
                  maxTicksLimit: 9
               }
            }
         },

         elements: {
            // Dot width
            point: {
               radius: 1,
               hoverRadius: 5
            },
            // Smooth lines
            line: {
               tension: 0.4
            }
         }
      }
   });


   // Doughnut Chart
   // ----------------------------------------------
   const circleData =[ 25, 35, 98, 49 ];
   const doughnutChart = new Chart( document.getElementById( "_dm-doughnutChart" ), {
      type: "doughnut",
      data: {
         labels: ["Resource usage", "Completed Projects", "Completed Tasks", "Earning"],
         datasets: [{
            data: circleData,
            borderColor: "transparent",
            backgroundColor: [dangerColor, warningColor, successColor, infoColor],
         }]
      },
      options: {
         responsive: true,
         maintainAspectRatio: false,
         plugins: {
            legend: {
               display: false
            },
         }
      }
   });

});



// Update the chart"s colors when the color scheme changes.
// ----------------------------------------------
const updateDashboardChart = function() {

   // Update all color variables
   updateColorVars();

   // Update sales chart
   lineChart.data.datasets[0].borderColor = primaryColor;
   lineChart.data.datasets[0].backgroundColor = primaryColor;
   lineChart.options.plugins.title.color = headingsColor;
   lineChart.options.plugins.legend.labels.color = primaryColor;
   lineChart.options.scales.y.grid.color = getGridYColor;
   lineChart.options.scales.x.grid.color = getGridXColor;
   lineChart.options.scales.y.ticks.color = headingsColor;
   lineChart.options.scales.x.ticks.color = headingsColor;

   lineChart.update();

};

[ "change.nf.colormode", "scheme-changed", "theme-changed" ].forEach( ev => document.addEventListener( ev, updateDashboardChart ))
