import { getToken } from '@/utils/auth.js'
const uploadConfig = {
  action: 'http://192.168.2.136:81/resource/cmsArticle/uploadImg',
  methods: 'POST',
  token: getToken(),
  name: 'file',
  size: 2000,
  accept: 'image/png, image/gif, image/jpeg, image/bmp, image/x-icon'
}

const toolOptions = [
  ['bold', 'italic', 'underline', 'strike'],
  [{ header: 1 }, { header: 2 }],
  [{ color: [] }, { background: [] }],
  [{ list: 'ordered' }, { list: 'bullet' }],
  [{ script: 'sub' }, { script: 'super' }],
  [{ indent: '-1' }, { indent: '+1' }],
  [{ direction: 'rtl' }],
  ['image'],
  [{ size: ['small', false, 'large', 'huge'] }],
  [{ font: [] }]
]
const handlers = {
  image: function image() {
    var self = this
    var fileInput = this.container.querySelector('input.ql-image[type=file]')
    if (fileInput === null) {
      fileInput = document.createElement('input')
      fileInput.setAttribute('type', 'file')
      if (uploadConfig.name) {
        fileInput.setAttribute('name', uploadConfig.name)
      }
      fileInput.setAttribute('accept', uploadConfig.accept)
      fileInput.classList.add('ql-image')
      fileInput.addEventListener('change', function() {
        var formData = new FormData()
        formData.append(uploadConfig.name, fileInput.files[0])
        var xhr = new XMLHttpRequest()
        xhr.open(uploadConfig.methods, uploadConfig.action, true)
        xhr.setRequestHeader('Authorization', 'Bearer ' + getToken())
        xhr.onload = function(e) {
          if (xhr.status === 200) {
            var res = JSON.parse(xhr.responseText)
            let length = self.quill.getSelection(true).index
            self.quill.insertEmbed(
              length,
              'image',
              'http://192.168.2.136:81/base/static' + res.data.imgUrl
            )
            self.quill.setSelection(length + 1)
          }
          fileInput.value = ''
        }
        xhr.upload.onloadstart = function(e) {
          fileInput.value = ''
        }
        xhr.upload.onerror = function(e) {}
        xhr.upload.onloadend = function(e) {}
        xhr.send(formData)
      })
      this.container.appendChild(fileInput)
    }
    fileInput.click()
  }
}

export default {
  placeholder: '请输入正文...',
  theme: 'snow',
  modules: {
    toolbar: {
      container: toolOptions,
      handlers: handlers
    }
  }
}
