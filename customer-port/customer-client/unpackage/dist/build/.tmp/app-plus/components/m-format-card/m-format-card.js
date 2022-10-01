(global["webpackJsonp"]=global["webpackJsonp"]||[]).push([["components/m-format-card/m-format-card"],{"39b8":function(t,e,n){"use strict";n.r(e);var r=n("c9c1"),a=n("4d4d");for(var c in a)"default"!==c&&function(t){n.d(e,t,function(){return a[t]})}(c);var u=n("2877"),o=Object(u["a"])(a["default"],r["a"],r["b"],!1,null,null,null);e["default"]=o.exports},"4d4d":function(t,e,n){"use strict";n.r(e);var r=n("e74b"),a=n.n(r);for(var c in r)"default"!==c&&function(t){n.d(e,t,function(){return r[t]})}(c);e["default"]=a.a},c9c1:function(t,e,n){"use strict";var r=function(){var t=this,e=t.$createElement;t._self._c},a=[];n.d(e,"a",function(){return r}),n.d(e,"b",function(){return a})},e74b:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0;var r={props:{cardNo:{type:[String,Number],default:""},isStar:Boolean},computed:{value:function(){var t=this.cardNo+"";return this.isStar?"".concat(t.slice(0,4),"******").concat(t.slice(t.length-4,t.length)):t.replace(/\s/g,"").replace(/[^\d]/g,"").replace(/(\d{4})(?=\d)/g,"$1 ")}}};e.default=r}}]);
;(global["webpackJsonp"] = global["webpackJsonp"] || []).push([
    'components/m-format-card/m-format-card-create-component',
    {
        'components/m-format-card/m-format-card-create-component':(function(module, exports, __webpack_require__){
            __webpack_require__('6e42')['createComponent'](__webpack_require__("39b8"))
        })
    },
    [['components/m-format-card/m-format-card-create-component']]
]);                
