#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;

out vec4 fragColor;

//vec3 textureUV(vec2 uv) {
//    vec3 color = vec3(0.);
//    color = vec3(uv.x, uv.y, 1.);
//    return color;
//}

void main(){
    //vec4 c = texture(DiffuseSampler, texCoord);
    //if(c.r<=0.0){
    //  fragColor = vec4(0.01,0.56,0.83,1.0);
    //}else{
      fragColor = texture(DiffuseSampler, texCoord);
    //}
}
