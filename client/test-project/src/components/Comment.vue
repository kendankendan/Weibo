<template>
  <div class="comment">
    <p ref="part"></p>
  </div>
</template>

<script>

export default {
  name: 'comment',
  mounted() {
    if (this.content!=undefined) {
      this.$refs.part.innerHTML = this.replace_em(this.content);
    }
  },
  data () {
    return {
      content: this.originContent
    }
  },
  props: {
    originContent: String
  },
  watch: {
    originContent: function() {
      this.content = this.originContent;
      if (this.content!=undefined) {
        this.$refs.part.innerHTML = this.replace_em(this.content);
      }
    }
  },
  components: {

  },
  methods: {
    replace_em(str){
      str = str.replace(/\</g,'&lt;');
      str = str.replace(/\>/g,'&gt;');
      str = str.replace(/\n/g,'<br/>');
      str = str.replace(/\[em_([0-9]*)\]/g,'<img src="static/arclist/$1.gif" border="0" />');
      return str;
    },
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.fw-body p {
  display: inline-block;
  margin: 0.2rem 0;
  padding-left: 1rem;
}
</style>
