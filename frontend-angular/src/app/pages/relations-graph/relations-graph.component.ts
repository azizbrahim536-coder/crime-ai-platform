import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Network } from 'vis-network/standalone';
import { GraphService } from '../../services/graph.service';

@Component({
  selector: 'app-relations-graph',
  templateUrl: './relations-graph.component.html',
  styleUrls: ['./relations-graph.component.css']
})
export class RelationsGraphComponent implements OnInit {

  affaireId!: number;

  constructor(
    private route: ActivatedRoute,
    private graphService: GraphService
  ) {}

  ngOnInit(): void {
    this.affaireId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadGraph();
  }

  loadGraph(): void {
    this.graphService.getAffaireGraph(this.affaireId).subscribe({
      next: (data) => {
        const container = document.getElementById('relationsGraph');

        if (!container) return;

        const nodes = data.nodes.map(node => ({
          id: node.id,
          label: node.label,
          group: node.group
        }));

        const edges = data.edges.map(edge => ({
          from: edge.from,
          to: edge.to,
          label: edge.label,
          arrows: 'to'
        }));

        const graphData = {
          nodes: nodes,
          edges: edges
        };

        const options = {
          nodes: {
            shape: 'dot',
            size: 20,
            font: {
              size: 14
            }
          },
          edges: {
            font: {
              align: 'middle'
            },
            color: '#555555'
          },
          groups: {
            AFFAIRE: {
              color: '#1e3a5f',
              shape: 'box'
            },
            CRIME: {
              color: '#dc2626'
            },
            SUSPECT: {
              color: '#f97316'
            },
            VICTIME: {
              color: '#16a34a'
            },
            TEMOIN: {
              color: '#7c3aed'
            },
            VILLE: {
              color: '#0284c7',
              shape: 'diamond'
            }
          },
          physics: {
            enabled: true
          }
        };

        new Network(container, graphData, options);
      },
      error: (err) => {
        console.error('Erreur chargement graph', err);
      }
    });
  }
}
