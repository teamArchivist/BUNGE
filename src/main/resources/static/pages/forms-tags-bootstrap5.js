// Import and initialize the Bootstrap5 Tags.
// ---------------------------------------------------------------------------------

// Here are some examples to help prevent errors when users open a page locally.
// ---------------------------------------------------------------------------------
try {
   const { default: Tags } = await import("../vendors/bootstrap5-tags/tags.min.js");
   Tags.init(".form-select.form-tags", {
      badgeStyle: "tags"
   });
} catch (error) {
   console.error("Couln't import Bootstrap5 Tags Plugins");
}


// Or use it instead of handling errors.
// ---------------------------------------------------------------------------------
// import Tags from "../vendors/bootstrap5-tags/tags.min.js";
// Tags.init(".form-select.form-tags", {
//    badgeStyle: "tags"
// });
