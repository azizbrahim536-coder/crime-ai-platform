export interface GraphNode {
  id: string;
  label: string;
  group: string;
}

export interface GraphEdge {
  from: string;
  to: string;
  label: string;
}

export interface GraphResponse {
  nodes: GraphNode[];
  edges: GraphEdge[];
}
