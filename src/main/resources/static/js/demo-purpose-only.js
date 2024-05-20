const body = document.body;
const root = document.getElementById("root");

// Create a custom event when the color mode or scheme changes.
// ---------------------------------------------------------------------------------
const colorChangedEv = new CustomEvent("scheme-changed");
const themeChangedEv = new CustomEvent("theme-changed");

if (document.getElementById("_dm-boxedBgContent")) {


   // BOXED LAYOUT WITH BACKGROUND IMAGE
   // ---------------------------------------------------------------------------------
   // HINT : Add the background image to the body or override the "--nf-boxed-layout-bg-image" CSS Variable.

   const boxedImgThumbs = [...document.querySelectorAll("._dm-boxbg__thumb")];

   boxedImgThumbs.map((boxedImgThumb) => {
      boxedImgThumb.addEventListener("click", (e) => {
         e.preventDefault();
         if (boxedImgThumb.classList.contains(".active")) return;


         let oldImg = document.querySelector("._dm-boxbg__thumb.active ")
         if (oldImg) oldImg.classList.remove("active");
         boxedImgThumb.classList.add("active");


         let targetIMG = boxedImgThumb.querySelector("img").getAttribute("src").replace("thumbs", "bg").replace("./","/");
         body.style.backgroundImage = `url( .${targetIMG} )`;
      });
   });
}





if (document.getElementById("_dm-settingsContainer")) {

   // Cache all radio and checkbox options.
   // ---------------------------------------------------------------------------------
   const conf = {
      boxedLayoutRadio:                   document.getElementById("_dm-boxedLayoutRadio"),
      centeredLayoutRadio:                document.getElementById("_dm-centeredLayoutRadio"),
      stickyHeaderCheckbox:               document.getElementById("_dm-stickyHeaderCheckbox"),
      stickyNavCheckbox:                  document.getElementById("_dm-stickyNavCheckbox"),
      miniNavRadio:                       document.getElementById("_dm-miniNavRadio"),
      maxiNavRadio:                       document.getElementById("_dm-maxiNavRadio"),
      pushNavRadio:                       document.getElementById("_dm-pushNavRadio"),
      slideNavRadio:                      document.getElementById("_dm-slideNavRadio"),
      revealNavRadio:                     document.getElementById("_dm-revealNavRadio"),
      disableBackdropCheckbox:            document.getElementById("_dm-disableBackdropCheckbox"),
      stuckSidebarCheckbox:               document.getElementById("_dm-stuckSidebarCheckbox"),
      pinnedSidebarCheckbox:              document.getElementById("_dm-pinnedSidebarCheckbox"),
      uniteSidebarCheckbox:               document.getElementById("_dm-uniteSidebarCheckbox")
   }


   // Initalize all options.
   // ---------------------------------------------------------------------------------
   conf.boxedLayoutRadio.checked =        body.classList.contains("boxed-layout");
   conf.centeredLayoutRadio.checked =     body.classList.contains("centered-layout");
   conf.stickyHeaderCheckbox.checked =    root.classList.contains("hd--sticky");
   conf.stickyNavCheckbox.checked =       root.classList.contains("mn--sticky");
   conf.miniNavRadio.checked =            root.classList.contains("mn--min");
   conf.maxiNavRadio.checked =            root.classList.contains("mn--max");
   conf.pushNavRadio.checked =            root.classList.contains("mn--push");
   conf.slideNavRadio.checked =           root.classList.contains("mn--slide");
   conf.revealNavRadio.checked =          root.classList.contains("mn--reveal");
   conf.disableBackdropCheckbox.checked = root.classList.contains("sb--bd-0");
   conf.stuckSidebarCheckbox.checked =    root.classList.contains("sb--stuck");
   conf.pinnedSidebarCheckbox.checked =   root.classList.contains("sb--pinned");
   conf.uniteSidebarCheckbox.checked =    root.classList.contains("sb--unite");





   // BOXED LAYOUT
   // ---------------------------------------------------------------------------------
   // HINT : Toggle the .boxed-layout class on BODY

   const boxedBgBtn = document.getElementById("_dm-boxedBgBtn");
   const boxedBgOption = document.getElementById("_dm-boxedBgOption");

   conf.boxedLayoutRadio.addEventListener("changed", (e) => {

      if (e.target.checked && !body.classList.contains("boxed-layout")) {

         // Set the current layout to Box mode
         body.classList.add("boxed-layout");

         // Enable the background images option
         boxedBgOption.classList.remove("opacity-50");
         boxedBgBtn.removeAttribute("disabled");

      } else {

         // Remove boxed layout
         body.classList.remove("boxed-layout");
         body.removeAttribute("style");

         // Disable the background images option
         boxedBgOption.classList.add("opacity-50");
         boxedBgBtn.setAttribute("disabled", true);
      }
   });





   // CENTERED LAYOUT
   // ---------------------------------------------------------------------------------
   // HINT : Toggle the .centered-layout class on BODY

   conf.centeredLayoutRadio.addEventListener("changed", () => {

      // Set the current layout to Center Mode.
      body.classList.toggle("centered-layout");


      // Change the main menu to Mini Mode.
      if (body.classList.contains("centered-layout")) {
         if ( root.classList.contains("mn--max") ) {
            conf.miniNavRadio.click();
         }
      }

   });





   // TRANSITION TIMING
   // ---------------------------------------------------------------------------------
   // HINT : Add your favorite transition timing class to the #root.

   const transitionSelect = document.getElementById("_dm-transitionSelect");
   transitionSelect.addEventListener("change", (e) => {
      const _this = e.target;

      // Toggle the selected attribute
      _this.querySelector("option[selected]").removeAttribute("selected");
      _this[_this.selectedIndex].setAttribute("selected", true);


      // Remove existing transition classes and add the currently selected transition class.
      body.classList.remove("in-quart", "out-quart", "in-back", "out-back", "in-out-back", "steps", "jumping", "rubber");
      body.classList.add(_this.value);

   });





   // STICKY HEADER
   // ---------------------------------------------------------------------------------
   // HINT : Toggle the .hd--sticky class on #root element.

   conf.stickyHeaderCheckbox.addEventListener("change", () => {

      // Toggle the sticky header class.
      root.classList.toggle("hd--sticky");

   });





   // ADDITIONAL OFFCANVAS
   // ---------------------------------------------------------------------------------
   // HINT : Please visit Bootstrap's documentation for more information and examples.
   // https://getbootstrap.com/docs/5.3/components/offcanvas/

   const offCanvasDemo = document.getElementById("_dm-offcanvas");
   const bsOffcanvas = new bootstrap.Offcanvas(offCanvasDemo);

   const settingToggler = document.getElementById("_dm-settingsToggler");
   const settingContainer = document.getElementById("_dm-settingsContainer");

   [...document.querySelectorAll("._dm-offcanvasBtn")].map((_btn) => {
      _btn.addEventListener("click", () => {

         // Set the offcanvas position to the user's choice.
         offCanvasDemo.className = `offcanvas ${_btn.value}`;
         offCanvasDemo.style = "transition-duration: 0s";


         // Hide the settings container and then show the additional offCanvas.
         settingToggler.dispatchEvent(new Event("click"));
         settingContainer.addEventListener("transitionend", () => {
            offCanvasDemo.style = "";
            bsOffcanvas.show();
         }, { once: true })

      })
   });





   // STICKY NAVIGATION
   // ---------------------------------------------------------------------------------
   // HINT : Toggle the .mn--sticky class on #root element.

   conf.stickyNavCheckbox.addEventListener("change", () => {

      // Toggle the sticky navigation class.
      root.classList.toggle("mn--sticky");

   });





   // PROILE WIDGET
   // ---------------------------------------------------------------------------------
   const mainNavProfile = document.querySelector("#_dm-mainnavProfile");
   document.getElementById("_dm-profileWidgetCheckbox").addEventListener("change", () => {

      // Toggle visibility
      mainNavProfile.classList.toggle("d-none");

   });





   // MIN NAVIGATION MODE
   // ---------------------------------------------------------------------------------
   conf.miniNavRadio.addEventListener("changed", (e) => {
      if ( !e.target.checked || root.classList.contains( "mn--min" ) ) return;

      // Set the navigation to Mini Mode.
      nifty.MainNavigation.setmode("mini");

   });





   // MAX NAVIGATION MODE
   // ---------------------------------------------------------------------------------
   conf.maxiNavRadio.addEventListener("changed", (e) => {
      if ( !e.target.checked || root.classList.contains( "mn--max" ) ) return;

      // Set the navigation to Maxi Mode.
      nifty.MainNavigation.setmode("maxi");

   });





   // PUSH NAVIGATION MODE
   // ---------------------------------------------------------------------------------
   conf.pushNavRadio.addEventListener("changed", (e) => {
      if ( !e.target.checked || root.classList.contains( "mn--push" ) ) return;

      // Set the navigation to Push Mode.
      nifty.MainNavigation.setmode("push");

   });





   // SLIDE NAVIGATION MODE
   // ---------------------------------------------------------------------------------
   conf.slideNavRadio.addEventListener("changed", (e) => {
     if ( !e.target.checked || root.classList.contains( "mn--slide" ) ) return;

      // Set the navigation to Slide on Top Mode.
      nifty.MainNavigation.setmode("slide");

   });





   // REVEAL NAVIGATION MODE
   // ---------------------------------------------------------------------------------
   conf.revealNavRadio.addEventListener("changed", (e) => {
     if ( !e.target.checked || root.classList.contains( "mn--reveal" ) ) return;

      // Set the navigation to Reveal Mode.
      nifty.MainNavigation.setmode("reveal");

   });


   // Update all nav radio status if the user toggles the navigation using the toggle button.
   document.addEventListener( "changed.nf.sidebar", () => conf[ `${ nifty.MainNavigation.mode }NavRadio` ].checked = true )



   // DISABLE SIDEBAR BACKDROP
   // ---------------------------------------------------------------------------------
   // HINT : Toggle the .sb--bd-0 class on #root element.

   conf.disableBackdropCheckbox.addEventListener("change", () => {

      // Hide the sidebar backdrop
      root.classList.toggle("sb--bd-0");

   });





   // STATIC SIDEBAR
   // ---------------------------------------------------------------------------------
   // HINT : Toggle the .sb--static class on #root element.

   document.getElementById("_dm-staticSidebarCheckbox").addEventListener("change", () => {

      // The sidebar will follow the page as you scroll down.
      root.classList.toggle("sb--static");

   });





   // STUCK SIDEBAR
   // ---------------------------------------------------------------------------------
   // The sidebar will stay in position until you click the close button.
   // ---------------------------------------------------------------------------------
   // HINT : Toggle the .sb--stuck class on #root element.

   conf.stuckSidebarCheckbox.addEventListener("change", () => {

      // Set the sidebar to Stuck mode.
      root.classList.toggle("sb--stuck");

   });





   // UNITE SIDEBAR
   // ---------------------------------------------------------------------------------
   // Transform the sidebar color into a header.
   // ---------------------------------------------------------------------------------
   // HINT : Toggle the .sb--stuck class on #root element.

   conf.uniteSidebarCheckbox.addEventListener("change", () => {

      // Set the sidebar to Unite mode.
      root.classList.toggle("sb--unite");

   });





   // PINNED SIDEBAR
   // ---------------------------------------------------------------------------------
   // The sidebar will look just like the rest of the content.
   // ---------------------------------------------------------------------------------
   // HINT : Toggle the .sb--pinned class on #root element.

   conf.pinnedSidebarCheckbox.addEventListener("change", () => {

      // Set the sidebar to pinned mode.
      root.classList.toggle("sb--pinned");

   });






   // COLOR SCHEMES
   // ---------------------------------------------------------------------------------
   // HINT : Change the data-scheme value on the HTML tag to match the available scheme names and the corresponding schema name.

   const colorScemesContainer = document.getElementById("dm_colorSchemesContainer");
   const colorSchemesBtn = colorScemesContainer.querySelectorAll("._dm-colorSchemes");
   const currentScheme = document.documentElement.getAttribute( "data-scheme" );

   colorSchemesBtn.forEach( csBtn => {
      if (csBtn.getAttribute( "data-color" ) == currentScheme) {
         csBtn.classList.add("active");
      }

      csBtn.addEventListener("click", ev => {
         document.documentElement.setAttribute( "data-scheme", ev.currentTarget.getAttribute("data-color") );
         colorScemesContainer.querySelector(".active").classList.remove("active");
         ev.currentTarget.classList.add("active");


         // Dispatch/Trigger/Fire the event
         document.dispatchEvent(colorChangedEv);
      })
   });


   // COLOR THEMES
   // ---------------------------------------------------------------------------------
   // HINT : Add the color theme class to the #root element.

   const colorModesContainer = document.getElementById("dm_colorModeContainer");
   const colorModesBtn = colorModesContainer.querySelectorAll( "._dm-colorModeBtn" );
   const modeClasses = [ "tm--expanded-hd", "tm--fair-hd", "tm--full-hd", "tm--primary-mn", "tm--primary-brand", "tm--tall-hd" ];

   const currentColorMode = modeClasses.filter( mc => {
      if( root.classList.contains(mc) ) return mc;
   }).toString();


   colorModesBtn.forEach( cmBtn => {
      if( cmBtn.getAttribute("data-color-mode") == currentColorMode )
         cmBtn.classList.add("active");

      cmBtn.addEventListener("click", ev => {
         const e = ev.currentTarget;
         if ( e.classList.contains( "active" ) ) return;

         // Change the root class
         root.classList.remove(...modeClasses);
         root.classList.add( e.getAttribute("data-color-mode") );

         colorModesBtn.forEach( item => {
            if (item.classList.contains("active")) item.classList.remove("active");
         })

         e.classList.add( "active" );

         // Dispatch/Trigger/Fire the event
         document.dispatchEvent(themeChangedEv);
      })
   })


   const fontSizeRange = settingContainer.querySelector("#_dm-fontSizeRange");
   const fontSizeBubble = settingContainer.querySelector("#_dm-fontSizeValue");
   const setRangeValue = ( range, bubble, preffix, suffix ) => {
      const val = range.value;
      const min = range.min ? range.min : 0;
      const max = range.max ? range.max : 100;
      const newVal = Number(((val - min) * 100) / (max - min));
      bubble.innerHTML = `${ preffix }${ val }${ suffix }`;

      // Sorta magic numbers based on size of the native UI thumb
      bubble.style.left = `calc(${newVal}% + (${8 - newVal * 0.15}px))`;
   }

   setRangeValue(fontSizeRange, fontSizeBubble, "", "px");
   fontSizeRange.addEventListener("input", e => {
      setRangeValue(e.currentTarget, fontSizeBubble, "", "px");
      document.documentElement.style.setProperty('font-size', `${e.currentTarget.value}px`);
   })


   // SCROLLBARS
   // ---------------------------------------------------------------------------------
   // Apply the OverlayScrollbars plugin to the body and .scrollable-content elements.


   // Get the assets folder path from the link tag element.
   // ---------------------------------------------------------------------------------
   const getLinkTag = (_type) => {
      let targetLink = null;
      [...document.getElementsByTagName("link")].map( (_link) => ( _link.href.includes( _type ) )? targetLink = _link:null );
      ( _type == "bootstrap" ) ? bootstrapLinkEl = targetLink: niftyLinkEl = targetLink;

      return targetLink;
   }

   const defaultBsUrl      = getLinkTag( "bootstrap." ).getAttribute( "href" );
   const assetsPath        = defaultBsUrl.match( /^.*?assets/g ).toString();



   // Overlayscrollbars Options
   // ---------------------------------------------------------------------------------
   const scrollbarOptions = {
      className: "os-nifty-minimal",
      scrollbars: {
         autoHide: "leave",
         clickScrolling: true
         //touchSupport: false
      },
      overflowBehavior: {
         x: "visible-scroll"
      }
   };



   // Add OverlayScrollbars CSS and JS to the document
   // ---------------------------------------------------------------------------------
   const addOverlayScrollbars = () => {
      return new Promise((resolve, reject) => {
         if (document.getElementById(`_dm-jsOverlayScrollbars`)) {
            resolve("done");
         } else {

            let _plugin = document.createElement("link");
            _plugin.setAttribute("id", "_dm-cssOverlayScrollbars");
            _plugin.setAttribute("rel", "stylesheet");
            _plugin.setAttribute("href", `${assetsPath}/vendors/overlayscrollbars/overlayscrollbars.min.css`);
            document.querySelector("head").append(_plugin);

            _plugin = document.createElement("script");
            _plugin.setAttribute("id", "_dm-jsOverlayScrollbars");
            _plugin.setAttribute("src", `${assetsPath}/vendors/overlayscrollbars/overlayscrollbars.browser.es6.min.js`);
            document.body.append(_plugin);

            _plugin.addEventListener("load", resolve, { once: true });
            _plugin.onerror = reject;
         }
      });
   }





   // Apply or destroy the OverlayScrollbars on the body.
   // ---------------------------------------------------------------------------------
   document.getElementById("_dm-bodyScrollbarCheckbox").addEventListener("change", async (e) => {
      await addOverlayScrollbars();
      const {OverlayScrollbars} = OverlayScrollbarsGlobal;
      if (e.target.checked) OverlayScrollbars(body, scrollbarOptions);
      else OverlayScrollbars(body).destroy();
   });





   // Apply or destroy the OverlayScrollbars on elements contain class name .scrollable-content.
   // ---------------------------------------------------------------------------------
   document.getElementById("_dm-sidebarsScrollbarCheckbox").addEventListener("change", async (e) => {
      await addOverlayScrollbars();

      const {OverlayScrollbars} = OverlayScrollbarsGlobal;
      const scrollableContent = document.querySelectorAll(".scrollable-content");

      if ( e.target.checked ) {
         scrollableContent.forEach( sc => OverlayScrollbars(sc, scrollbarOptions));
      } else {
         scrollableContent.forEach( sc => OverlayScrollbars(sc).destroy());
      }
   });

}





// Create custom events for radios to enable fire events when they go unchecked.
// ---------------------------------------------------------------------------------
const radioEvent = new Event( "changed" );

[...document.querySelectorAll("#_dm-settingsContainer input[type='radio']")].map((thisRadio) => {
   thisRadio.previous = thisRadio.checked;
   thisRadio.addEventListener("transitionend", (e) => {
      if (e.propertyName == "background-color" && thisRadio.previous != thisRadio.checked) {
         thisRadio.previous = thisRadio.checked;
         e.target.dispatchEvent(radioEvent);
      }
   })
});
