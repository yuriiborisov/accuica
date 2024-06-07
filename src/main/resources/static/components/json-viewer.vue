
<template>
  <div class="container-xxl">
    <div class="row h-40">
      <div class="col-md-12">
        <pre><code id="jsonViewer" class="json bg-dark text-white p-3" ></code></pre>
        <button class="btn btn-secondary mt-3 ml-2" v-on:click="this.copyToClipboard">Copy to Clipboard</button>
      </div>
    </div>
  </div>
</template>


<script>

module.exports = {

  props: {
    json: String
  },
  // created(){
  //
  // },
  mounted(){
    this.loadJson();
  },
  methods: {
    loadJson() {
      console.log('json', this.json);
      var jsonData = JSON.parse(this.json);
      var formattedJson = JSON.stringify(jsonData, null, 2); // Indentation level set to 2 for pretty printing
      document.getElementById('jsonViewer').textContent = formattedJson;
      hljs.highlightElement(document.getElementById('jsonViewer'));
    },
    copyToClipboard() {
      var textToCopy = document.getElementById('jsonViewer').textContent;
      navigator.clipboard.writeText(textToCopy).then(function() {
        alert('Copied to clipboard!');
      }, function(err) {
        console.error('Could not copy text: ', err);
      });
    },
  }
}
</script>
<style>
#jsonViewer {
  height: 60vh; /* Fixed height of 400 pixels */
  overflow-y: auto; /* Enable vertical scrolling if content exceeds the height */
}
</style>
