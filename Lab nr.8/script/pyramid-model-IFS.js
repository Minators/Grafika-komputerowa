function uvPyramid(height, side, slices) {
    height = height || 5.0; 
    side = side || 5.0; 
    slices = slices || 13; // Set slices to 13 for a 13-sided base

    var vertexCount = slices + 2; // 13 base vertices + apex + center base vertex
    var triangleCount = slices * 2; // 13 triangles for sides + 13 triangles for base

    var vertices = new Float32Array(vertexCount * 3);
    var normals = new Float32Array(vertexCount * 3);
    var texCoords = new Float32Array(vertexCount * 2);
    var indices = new Uint16Array(triangleCount * 3);

    var halfSide = side / 2.0;
    var kv = 0;
    var kt = 0;
    var k = 0;
    var i, u;

    // Vertex and normal for the apex
    vertices[kv] = 0;
    normals[kv++] = 0;
    vertices[kv] = height;
    normals[kv++] = 1;
    vertices[kv] = 0;
    normals[kv++] = 0;
    texCoords[kt++] = 0.5;
    texCoords[kt++] = 1.0;

    // Center vertex for the base
    vertices[kv] = 0;
    normals[kv++] = 0;
    vertices[kv] = 0;
    normals[kv++] = -1;
    vertices[kv] = 0;
    normals[kv++] = 0;
    texCoords[kt++] = 0.5;
    texCoords[kt++] = 0.5;

    for (i = 0; i < slices; i++) {
        u = i * 2 * Math.PI / slices;
        var c = Math.cos(u);
        var s = Math.sin(u);
        
        // Base vertices
        vertices[kv] = halfSide * c;
        normals[kv++] = 0;
        vertices[kv] = 0;
        normals[kv++] = -1;
        vertices[kv] = halfSide * s;
        normals[kv++] = 0;
        texCoords[kt++] = (c + 1) / 2;
        texCoords[kt++] = (s + 1) / 2;

        // Triangle indices for sides
        indices[k++] = 0;
        indices[k++] = i + 2;
        indices[k++] = ((i + 1) % slices) + 2;

        // Triangle indices for base
        indices[k++] = 1;
        indices[k++] = ((i + 1) % slices) + 2;
        indices[k++] = i + 2;
    }

    return {
        vertexPositions: vertices,
        vertexNormals: normals,
        vertexTextureCoords: texCoords,
        indices: indices
    };
}

var pyramid = uvPyramid(); // Create the pyramid
