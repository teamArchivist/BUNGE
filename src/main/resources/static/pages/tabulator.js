document.addEventListener( "DOMContentLoaded", () => {


   // DEFINE DATAS
   // ---------------------------------------------------------------------------------
   const simpleData = [
      {id:1, name:"Billy Bob", age:"12", gender:"Male", height:1, col:"red", dob:"", cheese:1},
      {id:2, name:"Mary May", age:"14", gender:"Female", height:2, col:"blue", dob:"14/05/1982", cheese:true},
      {id:3, name:"Christine Lobowski", age:"42", gender:"Female", height:0, col:"green", dob:"22/05/1982", cheese:"true"},
      {id:4, name:"Brendon Philips", age:"125", gender:"Male", height:1, col:"orange", dob:"01/08/1980"},
      {id:5, name:"Margret Marmajuke", age:"16", gender:"Female", height:5, col:"yellow", dob:"31/01/1999"}
   ]

   let moreData = [
      {id:6, name:"Frank Harbours", age:"12", gender:"Male", height:1, col:"red", dob:"", cheese:1},
      {id:7, name:"Gemma Jane", age:"1", gender:"Female", height:2, col:"blue", dob:"14/05/1982", cheese:true},
      {id:8, name:"Margret Marmajuke", gender:"Female", age:"42", height:0, col:"green", dob:"22/05/1982", cheese:"true"},
      {id:9, name:"James Newman", age:"125", gender:"Male", height:1, col:"orange", dob:"01/08/1980"},
      {id:10, name:"Brendon Philips", age:"16", gender:"Male", height:5, col:"yellow", dob:"31/01/1999"},
   ]

    // Join two arrays
    moreData = simpleData.concat(moreData);





   // DEFINE TABLES
   // ---------------------------------------------------------------------------------

   // Basic table
   // ---------------------------------------------------------------------------------
   const basicTable = new Tabulator( "#_dm-tabulatorBasic", {
      data: simpleData,
      autoColumns: true,
      layout:"fitColumns"
   })



   // Resizable Columns
   // ---------------------------------------------------------------------------------
   const tabulatorResizableColumns = new Tabulator("#_dm-tabulatorResizableColumns", {
      data: simpleData,
      layout:"fitColumns",
      columns:[
         {title:"Name", field:"name", sorter:"string", resizable:true, width:200, editor:true},
         {title:"Age", field:"age", sorter:"number", resizable:true, hozAlign:"center", headerHozAlign:"center" },
         {title:"Gender", field:"gender", sorter:"string", resizable:true },
         {title:"Height", field:"height", resizable:true, hozAlign:"center", width:100},
         {title:"Favourite Color", field:"col", sorter:"string", resizable:true},
         {title:"Date Of Birth", field:"dob", sorter:"date", resizable:true, hozAlign:"center"},
         {title:"Cheese Preference", field:"cheese", sorter:"boolean", resizable:true, hozAlign:"center"},
      ],
   });



   // Striped rows
   // ---------------------------------------------------------------------------------
   const stripedRows = new Tabulator("#_dm-tabulatorStriped", {
      data: simpleData,
      layout:"fitColumns",
      columns:[
         {title:"Name", field:"name", sorter:"string" },
         {title:"Age", field:"age", sorter:"number", hozAlign:"center", headerHozAlign:"center" },
         {title:"Gender", field:"gender", sorter:"string", hozAlign:"center", headerHozAlign:"center"},
         {title:"Favourite Color", field:"col", sorter:"string", hozAlign:"center", cssClass: "text-capitalize"}
      ],
   });



   // Bordered tables
   // ---------------------------------------------------------------------------------
   const borderedTables = new Tabulator("#_dm-tabulatorBorderedTable", {
      data: simpleData,
      layout:"fitColumns",
      columns:[
         {title:"Name", field:"name", sorter:"string" },
         {title:"Age", field:"age", sorter:"number", hozAlign:"center", headerHozAlign:"center" },
         {title:"Gender", field:"gender", sorter:"string", hozAlign:"center", headerHozAlign:"center"},
         {title:"Favourite Color", field:"col", sorter:"string", hozAlign:"center", cssClass: "text-capitalize"}
      ],
   });



   // Pagination tables
   // ---------------------------------------------------------------------------------
   const paginationTable = new Tabulator("#_dm-tabulatorPagination", {
      data: moreData,
      layout:"fitColumns",
      pagination:"local",
      paginationSize: 5,
      paginationSizeSelector:[ 3, 5, 7, 10],
      columns:[
         {title:"Name", field:"name", sorter:"string" },
         {title:"Age", field:"age", sorter:"number" },
         {title:"Gender", field:"gender", sorter:"string" },
         {title:"Favourite Color", field:"col", sorter:"string" }
      ],
   });



   // Editing data
   // ---------------------------------------------------------------------------------
   const editDataTable = new Tabulator("#_dm-tabulatorEditData", {
      data: moreData,
      layout:"fitColumns",
      pagination:"local",
      paginationSize: 5,
      paginationSizeSelector:[ 3, 5, 7, 10],
      columns:[
         {title:"Name", field:"name", sorter:"string", width: 200, editor:true },
         {title:"Age", field:"age", sorter:"number" },
         {title:"Gender", field:"gender", sorter:"string" },
         {title:"Favourite Color", field:"col", sorter:"string" }
      ],
   });



   // Collapsed list
   // ---------------------------------------------------------------------------------
   const collapsedList = new Tabulator("#_dm-tabulatorCollapsedList", {
      data: moreData,
      height:"300px",
      layout:"fitDataFill",
      responsiveLayout:"collapse",
      columns:[
         {formatter:"responsiveCollapse", width:40, minWidth:40, hozAlign:"center", resizable:false, headerSort:false},
         {title:"Name", field:"name", width:250, responsive:0},
         {title:"Age", field:"age", hozAlign:"center", headerHozAlign:"center", sorter:"number", width:150},
         {title:"Gender", field:"gender", width:150, responsive:2},
         {title:"Height", field:"height", hozAlign:"center", headerHozAlign:"center", width:150},
         {title:"Favourite Color", field:"col", width:150},
         {title:"Date Of Birth", field:"dob", hozAlign:"center", sorter:"date", width:150},
         {title:"Driver", field:"car", hozAlign:"center", width:150},
      ],
   });



   // Vertical header
   // ---------------------------------------------------------------------------------
   const verticalHeaders = new Tabulator("#_dm-tabulatorVerticalHeaders", {
      data: moreData,
      height:"315px",
      columns:[
         {title:"Name", field:"name", headerSort:false, headerVertical:true},
         {title:"Progress", field:"progress", sorter:"number", hozAlign:"left", formatter:"progress",  editable:true, headerSort:false, headerVertical:true},
         {title:"Gender", field:"gender", headerSort:false, headerVertical:true},
         {title:"Rating", field:"rating", hozAlign:"center", headerSort:false, headerVertical:true},
         {title:"Date Of Birth", field:"dob", hozAlign:"center", sorter:"date", headerSort:false, headerVertical:true},
         {title:"Driver", field:"car", hozAlign:"center", formatter:"tickCross", headerSort:false, headerVertical:true},
      ],
   });



   // Formatting data
   // ---------------------------------------------------------------------------------
   const questionIcon = () => '<i class="demo-psi-question fs-5"></i>';
   const formattingData = new Tabulator("#_dm-tabulatorFormatData", {
      data: moreData,
      layout:"fitColumns",
      height:"300px",
      columns:[
         {title:"Who", formatter: questionIcon, width:85, hozAlign:"center", cellClick:function(e, cell){alert("Printing row data for: " + cell.getRow().getData().name)}},
         {title:"Name", field:"name", sorter:"string", width:200, editor:true},
         {title:"Age", field:"age", sorter:"number", hozAlign:"left", formatter:"progress", cssClass:"tabulator-progress"},
         {title:"Gender", field:"gender", sorter:"string", hozAlign:"center" },
         {title:"Height", field:"height", formatter:"star", hozAlign:"center", width:100},
         {title:"Favourite Color", field:"col", hozAlign:"center", sorter:"string", formatter:"color"},
         {title:"Date Of Birth", field:"dob", sorter:"date", hozAlign:"center"},
         {title:"Cheese Preference", field:"cheese", sorter:"boolean", hozAlign:"center", formatter:"tickCross"},
      ],
   });



   // Filter Data
   // ---------------------------------------------------------------------------------
   // Define variables for input elements
   const fieldEl = document.getElementById( "_dm-filterField" );
   const typeEl  = document.getElementById( "_dm-filterType" );
   const valueEl = document.getElementById( "_dm-filterValue" );

   //Custom filter example
   function customFilter( data ) {
      return data.car && data.rating < 3;
   }

   //Trigger setFilter function with correct parameters
   function updateFilter(){
      let filterVal = fieldEl.options[fieldEl.selectedIndex].value;
      let typeVal = typeEl.options[typeEl.selectedIndex].value;

      const filter = filterVal == "function" ? customFilter : filterVal;

      if( filterVal == "function" ){
         typeEl.disabled = true;
         valueEl.disabled = true;
      } else {
         typeEl.disabled = false;
         valueEl.disabled = false;
      }

      if( filterVal ) filteredTable.setFilter(filter,typeVal, valueEl.value);
   }


   //Update filters on value change
   document.getElementById( "_dm-filterField").addEventListener( "change", updateFilter );
   document.getElementById( "_dm-filterType").addEventListener( "change", updateFilter );
   document.getElementById( "_dm-filterValue").addEventListener( "keyup", updateFilter );


   //Clear filters on "Clear Filters" button click
   document.getElementById( "_dm-filterClear").addEventListener( "click", function(){
      fieldEl.value = "none";
      typeEl.value = "like";
      valueEl.value = "";

      filteredTable.clearFilter();
   });


   //Build Tabulator
   const filteredTable = new Tabulator("#_dm-tabulatorFilter", {
      data: [
         {id:1, name:"Oli Bob", progress:12, location:"United Kingdom", gender:"Male", rating:1, col:"red", dob:"14/04/1984", car:1, lucky_no:5, lorem:"Lorem ipsum dolor sit amet, elit consectetur adipisicing "},
         {id:2, name:"Mary May", progress:7, location:"Germany", gender:"Female", rating:2, col:"blue", dob:"14/05/1982", car:true, lucky_no:10, lorem:"Lorem ipsum dolor sit amet, elit consectetur adipisicing "},
         {id:3, name:"Christine Lobowski", progress:42, location:"France", gender:"Female", rating:0, col:"green", dob:"22/05/1982", car:"true", lucky_no:12, lorem:"Lorem ipsum dolor sit amet, elit consectetur adipisicing "},
         {id:4, name:"Brendon Philips", progress:100, location:"USA", gender:"Male", rating:1, col:"orange", dob:"01/08/1980", car:false, lucky_no:18, lorem:"Lorem ipsum dolor sit amet, elit consectetur adipisicing "},
         {id:5, name:"Margret Marmajuke", progress:16, location:"Canada", gender:"Female", rating:5, col:"yellow", dob:"31/01/1999", car:false, lucky_no:33, lorem:"Lorem ipsum dolor sit amet, elit consectetur adipisicing "},
         {id:6, name:"Frank Harbours", progress:38, location:"Russia", gender:"Male", rating:4, col:"red", dob:"12/05/1966", car:1, lucky_no:2, lorem:"Lorem ipsum dolor sit amet, elit consectetur adipisicing "},
         {id:7, name:"Jamie Newhart", progress:23, location:"India", gender:"Male", rating:3, col:"green", dob:"14/05/1985", car:true, lucky_no:63, lorem:"Lorem ipsum dolor sit amet, elit consectetur adipisicing "},
         {id:8, name:"Gemma Jane", progress:60, location:"China", gender:"Female", rating:0, col:"red", dob:"22/05/1982", car:"true", lucky_no:72, lorem:"Lorem ipsum dolor sit amet, elit consectetur adipisicing "},
         {id:9, name:"Emily Sykes", progress:42, location:"South Korea", gender:"Female", rating:1, col:"maroon", dob:"11/11/1970", car:false, lucky_no:44, lorem:"Lorem ipsum dolor sit amet, elit consectetur adipisicing "},
         {id:10, name:"James Newman", progress:73, location:"Japan", gender:"Male", rating:5, col:"red", dob:"22/03/1998", car:false, lucky_no:9, lorem:"Lorem ipsum dolor sit amet, elit consectetur adipisicing "},
      ],
      height:"300px",
      layout:"fitColumns",
      columns:[
         {title:"Name", field:"name", width:200},
         {title:"Progress", field:"progress", formatter:"progress", cssClass:"tabulator-progress", sorter:"number"},
         {title:"Gender", field:"gender"},
         {title:"Rating", field:"rating", formatter:"star", hozAlign:"center", width:100},
         {title:"Favourite Color", field:"col"},
         {title:"Date Of Birth", field:"dob", hozAlign:"center", sorter:"date"},
         {title:"Driver", field:"car", hozAlign:"center", formatter:"tickCross"},
      ],
   });
})
